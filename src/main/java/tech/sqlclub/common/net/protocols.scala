package tech.sqlclub.common.net

import tech.sqlclub.common.utils.JacksonUtils

/**
  * 网络套接字消息接口
  */
sealed trait SocketMessage {
  /**
    * 转成json string格式
    * @return json字符串
    */
  def json:String
}

// protocols
trait Request extends SocketMessage {
  /**
    * 请求数据包装
    * @return Request
    */
  def wrap: Any

  override def json: String = JacksonUtils.toJson(wrap)
}

trait Response extends SocketMessage {
  /**
    * 响应数据包装
    * @return Response
    */
  def wrap: Any

  override def json: String = JacksonUtils.toJson(wrap)
}


object SocketReplyMark extends Enumeration{
  type Type = Value
  val HEAD = Value(-2)
  val END = Value(-1)
  val LEN_DATA = Value(-99)
}
