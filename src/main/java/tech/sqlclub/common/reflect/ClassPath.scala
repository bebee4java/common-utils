package tech.sqlclub.common.reflect

import java.lang.reflect.Method
import tech.sqlclub.common.annotation.Explanation
import scala.reflect.ClassTag

/**
  *
  * Created by songgr on 2020/10/24.
  */
class ClassPath[T: ClassTag](val clazz: Class[T], val instance:Option[T]) {

  lazy val classLoader: ClassLoader =
    if(instance.isDefined) instance.get.getClass.getClassLoader else clazz.getClassLoader

  def fullClassName = clazz.getName

  def simpleClassName = clazz.getSimpleName

  def packageName = clazz.getPackage.getName

  def allMethods: Array[Method] = clazz.getMethods

}

object ClassPath {

  def from[T: ClassTag](clazz: Class[T]): ClassPath[T] = {
    new ClassPath[T](clazz, None)
  }

  def from[T: ClassTag](className: String): ClassPath[T] = {
    val clazz = Class.forName(className)
    new ClassPath[T](clazz.asInstanceOf[Class[T]], None)
  }

  @Explanation(attention = "请用该方式调用对象方法")
  def fromInstance[T: ClassTag](obj: T): ClassPath[T] = {
    new ClassPath[T](obj.getClass.asInstanceOf[Class[T]], Option(obj))
  }

}
