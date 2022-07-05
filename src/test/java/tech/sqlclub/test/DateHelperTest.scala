package tech.sqlclub.test

import org.scalatest.FunSuite
import tech.sqlclub.common.time.DateHelper

/**
  * DateHelper 测试
  * Created by songgr on 2022/05/31.
  */
class DateHelperTest extends FunSuite {

  test("DateHelperTest") {
    import DateHelper._

    // 2天前
    println(2 days ago)
    // 5天后
    println(5 days after_now)
    // 一周前
    println(1 weeks ago)

    // 3天前 指定日期
    println(3 days(ago, "2022-05-31 10:00:00") )
    // 4个月前 指定日期
    println(4 months(ago, "2022-05-31 10:00:00") )
  }
}
