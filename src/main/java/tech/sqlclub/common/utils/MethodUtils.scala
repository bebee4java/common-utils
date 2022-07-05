package tech.sqlclub.common.utils

import tech.sqlclub.common.exception.SQLClubException

/**
  *
  * Created by songgr on 2022/02/25.
  */
object MethodUtils {

  /**
    * 调用错误后抛出自定义消息异常
   */
  @throws(classOf[SQLClubException])
  def callErrorThenThrow[T](f: () => T, errorMsg:String):T = {
    try {
      f.apply()
    } catch {
      case t : Throwable => throw new SQLClubException(errorMsg, t)
    }
  }

}
