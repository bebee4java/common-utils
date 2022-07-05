package tech.sqlclub.test

import org.scalatest.FunSuite

/**
  * 正则测试
  * Created by songgr on 2022/05/31.
  */
class RegexOpTest extends FunSuite {


  test("regex") {
    import tech.sqlclub.common.regex.RegexOp._

    println("aaasdds" matching ".+" )

    println("aaa" matching "a.*") //true

    println("aaa" matching "b.*") //false

    println("a123".matchGroup("(a)(\\d+)", 1).toList ) //List(a)

    println("a123".matchGroup("(a)(\\d+)", 2).toList ) //List(123)

    println("a1a1a" matchAll "a") //List(a, a, a)

    println("a1a1a" matchFirst  "a") //Some(a)

    println("13811111111" isMobileNumber ) //true

    println(
      """
        |13811111111
        |电话:
        |18222222222
      """.stripMargin extractMobileNumber ) //List(13811111111, 18222222222)

    println("-1.0" isMumeric) //true
    println(
      """
        |这是一段文本
        |-1 and -3.0
        |大于 100 +1000
      """.stripMargin extractMumeric) // List(-1, -3.0, 100, +1000)

  }


}
