package tech.sqlclub.test

import org.scalatest.FunSuite
import tech.sqlclub.common.utils.CaseClassUtils

/**
  *
  * Created by songgr on 2021/01/07.
  */
class CaseClassUtilsTest extends FunSuite {

  test("getallField") {

    val c1 = CaseClassUtils.caseClassFields(C1(1, "2"))
    c1.foreach{println}
  }

  test("typeCheck") {
    import CaseClassUtils._
    val int1:Int = 129000
    val int2:Integer = 129000
    println(int1 == int2) // true

    println(JInteger == SInt) // false
  }

}
case class C1 (a:Int, b:String)