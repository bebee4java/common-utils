package dt.sqlclub.common.utils

object NumberUtils {


  def getIntValue( obj:Object, defaultValue:Int) = {
    if (obj == null) {
      defaultValue
    } else {
      try {
        Integer.parseInt(obj.toString)
      } catch {
        case e:Exception => defaultValue
      }
    }

  }

}
