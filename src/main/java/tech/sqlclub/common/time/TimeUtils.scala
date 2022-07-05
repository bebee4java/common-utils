package tech.sqlclub.common.time

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTime, DateTimeZone, Duration}

import java.util.{Date, TimeZone}

/**
  *
  * Created by songgr on 2022/05/30.
  */
object TimeUtils {

  lazy val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  def localTimeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+8:00"))

  def now = DateTime.now(localTimeZone)

  def currentLocaltime: String = now.toString(format)

  def currentLocalDate: Date = now.toDate

  def currentTimestamp: Long = currentLocalDate.getTime

  def dateDiff(date1: String, date2: String): Long = {
    val dt1 = DateTime.parse(date1, format)
    val dt2 = DateTime.parse(date2, format)
    val duration = new Duration(dt1, dt2)
    duration.getStandardSeconds
  }

  def dateFormat(date: Date, format: String = "yyyy-MM-dd HH:mm:ss")
                (implicit f: DateTimeFormatter = DateTimeFormat.forPattern(format)): String =
    new DateTime(date.getTime, localTimeZone).toString(f)

}
