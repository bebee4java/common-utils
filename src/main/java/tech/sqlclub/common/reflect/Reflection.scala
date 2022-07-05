package tech.sqlclub.common.reflect

import java.lang.annotation.Annotation
import org.reflections.Reflections
import tech.sqlclub.common.annotation.Explanation
import tech.sqlclub.common.utils.ArrayUtils
import scala.collection.JavaConverters._
import scala.reflect.runtime.{universe => ru}

/**
  *
  * Created by songgr on 2020/10/25.
  */
class Reflection(classPath: ClassPath[_]) {

  lazy private val mirror = ru.runtimeMirror(classPath.classLoader)

  def getFieldNames: Array[String] = {
    Class.forName(classPath.fullClassName)
      .getDeclaredFields
      .filterNot(_.isSynthetic)  // 排除合成字段
      .map(_.getName)
  }

  def getOrSetFieldByName(name:String, value:Option[Any] = None):Any = {
    val instanceOption = classPath.instance
    if (instanceOption.isDefined) {
      val instance = instanceOption.get
      val instanceMirror = mirror.reflect(instance)
      val term = instanceMirror.symbol.typeSignature.member(ru.TermName(name))
      // scala 完全面向对象，只能访问对象属性，没有静态的操作
      if (term.isTerm) {
        val field = instanceMirror.reflectField(term.asTerm)
        if (value.isDefined) {
          field.set(value.get)
        } else {
          return field.get
        }
      } else {
        // 为了继续访问java类静态属性，需要使用java的反射
        try {
          val field = instance.getClass.getDeclaredField(name)
          field.setAccessible(true)
          return field.get(instance)
        } catch {
          case e: NoSuchFieldException => throw new RuntimeException(s"NoSuchFieldException: $name in class ${classPath.fullClassName}")
        }
      }
    } else {
      val field = try {
        Option(Class.forName(classPath.fullClassName).getField(name))
      } catch {
        case _:NoSuchFieldException => None
      }
      assert(field.isDefined, s"NoSuchFieldException: $name in class ${classPath.fullClassName}")

      if (value.isDefined) {
        field.get.set(null, value.get)
      } else {
        field.get.get()
      }
    }
  }

  @Explanation(
    description = "通过方法名反射方法调用",
    attention = "方法名在类中必须唯一，如果存在方法重载请参考#invoke"
  )
  def callMethodByName(name: String, objs: Any*) = {
    val instance = classPath.instance
    if (instance.isDefined) {
      val instanceMirror = mirror.reflect(instance.get)
      val method = instanceMirror.symbol.typeSignature.member(ru.TermName(name))

      assert(method.isMethod && method.asMethod.paramLists.count(_.nonEmpty) == objs.length,
        s"NoSuchMethodException: ${classPath.fullClassName}.${name}(${objs.map(_.getClass.getName).mkString(",")})")

      instanceMirror.reflectMethod(method.asMethod)(objs: _*)
    } else {
      val methods = Class.forName(classPath.fullClassName).getMethods.filter(
        m =>
          m.getName.equals(name)
            && m.getParameterCount == objs.length
      )

      assert(methods.length == 1,
        s"NoSuchMethodException: ${classPath.fullClassName}.${name}(${objs.map(_.getClass.getName).mkString(",")})")

      methods(0).invoke(null, objs.map(_.asInstanceOf[Object]):_*)
    }
  }

  @Explanation(
    description = "通过方法名和形参类型反射方法",
    attention = "如果类中有方法重载，必须正确指定形参类型和传入的参数个数"
  )
  def invoke(name: String, clazzs: Class[_]*)(objs: Any*) = {
    val instance = classPath.instance
    if (instance.isDefined) {
      val instanceMirror = mirror.reflect(instance.get)
      val term = instanceMirror.symbol.typeSignature.member(ru.TermName(name))
      assert(term.isTerm,
        s"NoSuchMethodException: ${classPath.fullClassName}.${name}(${objs.map(_.getClass.getName).mkString(",")})")

      val method = term.asTerm.alternatives.find {
        s =>
          val paramtypes = s.asMethod.paramLists.flatMap(_.map(_.typeSignature.typeSymbol.fullName))
          val clazzTypes = clazzs.map(_.getName)
          ArrayUtils.equals(paramtypes.toArray, clazzTypes.toArray)
      }

      assert(method.isDefined,
        s"NoSuchMethodException: ${classPath.fullClassName}.${name}(${objs.map(_.getClass.getName).mkString(",")})")

      instanceMirror.reflectMethod(method.get.asMethod)(objs: _*)
    } else {
      val method = try {
        Option(Class.forName(classPath.fullClassName).getMethod(name, clazzs:_*))
      }catch {
        case _:NoSuchMethodException => None
      }

      assert(method.isDefined,
        s"NoSuchMethodException: ${classPath.fullClassName}.${name}(${objs.map(_.getClass.getName).mkString(",")})")

      method.get.invoke(null, objs.map(_.asInstanceOf[Object]):_*)
    }
  }

  def instance[T]:T = {
    try {
      Class.forName(classPath.fullClassName + "$")
      // reflect scala object
      val module = mirror.staticModule(classPath.fullClassName)
      val obj = mirror.reflectModule(module)
      obj.instance.asInstanceOf[T]
    } catch {
      case _:ClassNotFoundException =>
        // reflect class
        val constructor = classPath.clazz.getConstructor()
        constructor.newInstance().asInstanceOf[T]
    }
  }

  def createInstance[T](clazzs: Class[_]*)(objs: Any*):T = {
    val constructor = classPath.clazz.getConstructor(clazzs:_*)
    constructor.newInstance(objs.map(_.asInstanceOf[Object]):_*).asInstanceOf[T]
  }

}

object Reflection {

  def apply(classPath: ClassPath[_]): Reflection = {
    new Reflection(classPath)
  }

  /**
    * 获取所有实现指定注解的类
    *
    * allClassWithAnnotation(classOf[myAnnotation], "my.package", classLoader);
    * //or
    * allClassWithAnnotation(classOf[myAnnotation], "my.package", someScanner, anotherScanner, classLoader);
    * //or
    * allClassWithAnnotation(classOf[myAnnotation], myUrl, myOtherUrl);
    *
    * @param annotation 注解类类型
    * @param params 参数支持: String/Class/ClassLoader/org.reflections.scanners.Scanner/java.net.URL/Object[] 参见ConfigurationBuilder.build
    * @return
    */
  def allClassWithAnnotation(annotation:Class[_ <:Annotation], params: Any*)= {
    val reflections = new Reflections(params.map(_.asInstanceOf[Object]):_*)
    reflections.getTypesAnnotatedWith(annotation).asScala.toSet
  }

  def getAnnotation[A <: Annotation](clazz : Class[_], a:Class[A]):A = clazz.getAnnotation[A](a)

  def allSubtypeClasses(clzz:Class[_], params: Any*) = {
    val reflections = new Reflections(params.map(_.asInstanceOf[Object]):_*)
    reflections.getSubTypesOf(clzz).asScala.toSet
  }

}
