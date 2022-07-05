package tech.sqlclub.common.time

import tech.sqlclub.common.utils.Assert
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit._
import java.util.Date

/**
  * 时间加减处理类
  * Created by songgr on 2022/05/30.
  */
class DateHelper(offset: Int)(implicit func:String => LocalDate = null) {

  // 加减秒
  def seconds(when: String, time:String= null): LocalDate = {
    val now  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => now.minus(offset, SECONDS)
      case DateHelper.after_now => now.plus(offset, SECONDS)
      case _ => now
    }
  }

  // 加减分钟
  def minutes(when: String, time:String= null): LocalDate = {
    val now  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => now.minus(offset, MINUTES)
      case DateHelper.after_now => now.plus(offset, MINUTES)
      case _ => now
    }
  }

  // 加减小时
  def hours(when: String, time:String= null): LocalDate = {
    val now  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => now.minus(offset, HOURS)
      case DateHelper.after_now => now.plus(offset, HOURS)
      case _ => now
    }
  }

  // 加减天数
  def days(when: String, time:String= null): LocalDate = {
    val today  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => today.minusDays(offset)
      case DateHelper.after_now => today.plusDays(offset)
      case _ => today
    }
  }

  // 加减周
  def weeks(when: String, time:String= null): LocalDate = {
    val today  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => today.minusWeeks(offset)
      case DateHelper.after_now => today.plusWeeks(offset)
      case _ => today
    }
  }

  // 加减月数
  def months(when: String, time:String= null): LocalDate = {
    val today  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => today.minusMonths(offset)
      case DateHelper.after_now => today.plusMonths(offset)
      case _ => today
    }
  }

  // 加减年数
  def years(when: String, time:String= null): LocalDate = {
    val today  = if (func != null && time != null) func(time) else LocalDate.now
    when match {
      case DateHelper.ago => today.minusYears(offset)
      case DateHelper.after_now => today.plusYears(offset)
      case _ => today
    }
  }

}

object DateHelper {
  val ago = "ago"
  val after_now = "after_now"

  implicit def convertInt2DateHelper(offset: Int): DateHelper = new DateHelper(offset)

  import tech.sqlclub.common.regex.RegexOp._
  implicit def at(time:String):LocalDate = {
    Assert.isTrue(time matching "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-2][0-9](:[0-5][0-9]){2}", "请输入正确的日期格式：yyyy-MM-dd HH:mm:ss")
    LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  }

  import java.time.ZoneId
  lazy val defaultZoneId: ZoneId = ZoneId.systemDefault

  implicit def localDate2Date(localDate: LocalDate): Date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant)
}
