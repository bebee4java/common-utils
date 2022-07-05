package tech.sqlclub.test

import org.scalatest.FunSuite
import tech.sqlclub.common.annotation.Explanation
import tech.sqlclub.common.reflect.{ClassPath, Reflection}

/**
  *
  * Created by songgr on 2020/10/24.
  */
@Explanation(description = "AAAA")
class AAA {
  def A1() = println(s"AAA.A1")

  def A2(str: String) = println(s"AAA.A2: $str")
  def A2(str:String, int:Integer) = println(s"AAA.A2: $str, $int")
}

@Explanation(description = "AAAA")
object BBB {
  def B1 = println("BBB.B1")

  def B2(str: String="ss") = println(s"BBB.B2: $str")
  def B2(str:String, int:Integer) = println(s"BBB.B2: $str, $int")
  def B2(str:String, ls:List[Integer]) = println(s"BBB.B2: $str, ${ls.mkString(",")}")
}
class BBB { }


class ReflectSuite extends FunSuite {

  test("ClassPath1") {
    val classpath = ClassPath.fromInstance(new AAA)
//    val classpath = ClassPath.from("tech.sqlclub.test.AAA")
//    val classpath = ClassPath.from(classOf[AAA])
    println(classpath.fullClassName)
    println(classpath.simpleClassName)
    println(classpath.packageName)

    val reflection = Reflection(classpath)

    reflection.callMethodByName("A1")
    reflection.invoke("A2", classOf[String])("1")
    reflection.invoke("A2", classOf[String], classOf[Integer])("1", 1)
//    reflection.callMethodByName("A3", "1", 1)

  }

  test("ClassPath2") {
    val classpath = ClassPath.from("tech.sqlclub.test.BBB")
//    val classpath = ClassPath.from(classOf[BBB])
    println(classpath.fullClassName)
    println(classpath.simpleClassName)
    println(classpath.packageName)

    val reflection = Reflection(classpath)

    val value = reflection.instance[Object]

    reflection.callMethodByName("B1")
    reflection.invoke("B2", classOf[String])("xxx")
    reflection.invoke("B2", classOf[String], classOf[Integer])("xxx", 1)
    reflection.invoke("B2", classOf[String], classOf[List[Integer]])("xxx", List(1,2,3))
//    reflection.callMethodByName("B1", 1)
//    reflection.callMethodByName("B3")
  }


  /**
    * 调用对象方法只有一种方式：如ClassA.testA2()
    * ClassPath.fromInstance(new ClassA())
    */
  test("ClassPath3") {
//    val classpath = ClassPath.from("tech.sqlclub.test.ClassA")
//    val classpath = ClassPath.from(classOf[ClassA])
    val classpath = ClassPath.fromInstance(new ClassA())
    val reflection = Reflection(classpath)
    reflection.callMethodByName("testA2")
    reflection.invoke("testA2")()

  }

  /**
    * 调用类静态方法有两种方式：如ClassA.testA1()
    * 1. ClassPath.from("tech.sqlclub.test.ClassA")
    * 2. ClassPath.from(classOf[ClassA])
    *
    * 注意：ClassPath.fromInstance(new ClassA()) 这种方式是不对的，在对象上找不到静态方法
    */
  test("ClassPath4") {
        val classpath = ClassPath.from("tech.sqlclub.test.ClassA")
//        val classpath = ClassPath.from(classOf[ClassA])
//    val classpath = ClassPath.fromInstance(new ClassA())
    val reflection = Reflection(classpath)
    reflection.callMethodByName("testA1")
    reflection.invoke("testA1")()

  }


  test("Field test"){
    val classpath = ClassPath.from("tech.sqlclub.test.ClassA")
    val reflection = Reflection(classpath)
    val f = reflection.getOrSetFieldByName("ss")
    println(f)
    reflection.getOrSetFieldByName("ss", Option("xxxx"))
    println(reflection.getOrSetFieldByName("ss"))

    // 类名反射拿不到对象变量
//    println(reflection.getOrSetFieldByName("s"))
//    reflection.getOrSetFieldByName("s", Option("xxxx"))
//    println(reflection.getOrSetFieldByName("s"))
  }

  case class AAAA(a:String, b:Int)

  test("Field test2"){
    val classpath = ClassPath.fromInstance( new ClassA() )
    val reflection = Reflection(classpath)
    val f = reflection.getOrSetFieldByName("ss")
    println(f)
    reflection.getOrSetFieldByName("s", Option("xxxx"))
    println(reflection.getOrSetFieldByName("s"))

    // 对象反射拿不到静态变量
//    println(reflection.getOrSetFieldByName("ss"))
//    reflection.getOrSetFieldByName("ss", Option("xxxx"))
//    println(reflection.getOrSetFieldByName("ss"))
  }

  test("getFieldNames") {
//    val classpath = ClassPath.from("tech.sqlclub.test.ClassA")
//    val classpath = ClassPath.fromInstance(new ClassA())
    val classpath = ClassPath.fromInstance(AAAA("ss", 1))
    val reflection = Reflection(classpath)
    println(reflection.getFieldNames.mkString(","))

  }

  test("Reflection Test"){
    println(Reflection.allClassWithAnnotation(classOf[Explanation]))

    println(Reflection.getAnnotation(classOf[AAA], classOf[Explanation]).description())
  }


  test("Reflection instance") {
    val classpath = ClassPath.from(classOf[ClassA])
    val reflection = Reflection(classpath)
    reflection.instance[ClassA]
    reflection.createInstance[ClassA](classOf[String])("sss")
  }

  test("object instance") {

    val c1 = ClassPath.from("tech.sqlclub.test.X1")
    val c2 = ClassPath.from("tech.sqlclub.test.X2")

    val o1 = Reflection(c1).instance[X2]

    val o2 = Reflection(c2).instance[X2]

    println(o1)
    println(o2)
  }

}

object X1 extends X2

class X2

