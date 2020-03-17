package tech.sqlclub.common.net

import tech.sqlclub.common.utils.JacksonUtils

sealed trait SocketMessage {
  def json:String
}

// protocols
trait Request extends SocketMessage {
  def wrap: Any

  override def json: String = JacksonUtils.toJson(wrap)
}

trait Response extends SocketMessage {
  def wrap: Any

  override def json: String = JacksonUtils.toJson(wrap)
}


object SocketReplyMark extends Enumeration{
  type Type = Value
  val HEAD = Value(-2)
  val END = Value(-1)
  val LEN_DATA = Value(-99)
}
