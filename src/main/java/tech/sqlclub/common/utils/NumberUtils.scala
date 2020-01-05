package tech.sqlclub.common.utils

import java.lang._

object NumberUtils {


  def getIntValue( obj:Object, defaultValue:Integer):Integer = {
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

  def getLongValue( obj:Object, defaultValue:Long):Long = {
    if (obj == null) {
      defaultValue
    } else {
      try {
        Long.parseLong(obj.toString)
      } catch {
        case e:Exception => defaultValue
      }
    }
  }

  def getDoubleValue( obj:Object, defaultValue:Double):Double = {
    if (obj == null) {
      defaultValue
    } else {
      try {
        Double.parseDouble(obj.toString)
      } catch {
        case e:Exception => defaultValue
      }
    }

  }

  def getFloatValue( obj:Object, defaultValue:Float):Float = {
    if (obj == null) {
      defaultValue
    } else {
      try {
        Float.parseFloat(obj.toString)
      } catch {
        case e:Exception => defaultValue
      }
    }
  }

}
