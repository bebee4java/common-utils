package tech.sqlclub.common.exception

/**
  *
  * Created by songgr on 2019/12/19.
  */
class SQLClubException(
  val message:String,
  val cause: Throwable) extends Exception(message, cause) {

  def this(message:String) = this(message, null)
}
