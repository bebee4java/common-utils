package tech.sqlclub.common.utils

import scala.collection.JavaConversions._
import java.util

/**
  *
  * Created by songgr on 2020/01/04.
  */
case class ParamMapUtils() {
  var map:Map[String,Object] = _
  def this(map:Map[String,Object]) = {
    this()
    this.map = map
  }

  def getStringValue(key: String, default: String = null, paramMap: Map[String, AnyRef] = map): String = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[String]
    } catch {
      case e: Exception =>
    }
    default
  }

  def getIntValue(key: String, default:Int = 0, paramMap: Map[String, AnyRef] = map ): Int = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[Int]
    } catch {
      case e: Exception =>
    }
    default
  }

  def getLongValue(key: String, default:Long = 0L, paramMap: Map[String, AnyRef] = map ): Long = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[Long]
    } catch {
      case e: Exception =>
    }
    default
  }

  def getDoubleValue(key: String, default:Double = 0d, paramMap: Map[String, AnyRef] = map ): Double = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[Double]
    } catch {
      case e: Exception =>
    }
    default
  }


  def getBooleanValue(key: String, default:Boolean = true, paramMap: Map[String, AnyRef] = map ): Boolean = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[Boolean]
    } catch {
      case e: Exception =>
    }
    default
  }


  def getAnyRefMap(key: String, default:Map[String,AnyRef] = null, paramMap: Map[String, AnyRef] = map ): Map[String,AnyRef] = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[util.Map[String,AnyRef]].toMap
    } catch {
      case e: Exception =>
    }
    default
  }


  def getStringMap(key: String, default:Map[String,String] = null, paramMap: Map[String, AnyRef] = map ): Map[String,String] = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[util.Map[String,String]].toMap
    } catch {
      case e: Exception =>
    }
    default
  }

  def getListMap(key: String, default:List[Map[String,AnyRef]] = null, paramMap: Map[String, AnyRef] = map ): List[Map[String,AnyRef]] = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[util.List[util.Map[String,AnyRef]]].toList.map(_.toMap)
    } catch {
      case e: Exception =>
    }
    default
  }

  def getListStringMap(key: String, default:List[Map[String,String]] = null, paramMap: Map[String, AnyRef] = map ): List[Map[String,String]] = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) return obj.asInstanceOf[util.List[util.Map[String,String]]].toList.map(_.toMap)
    } catch {
      case e: Exception =>
    }
    default
  }


  def getAny[T](key:String, default:T = null, paramMap: Map[String, AnyRef] = map): T = {
    try {
      val obj = paramMap.get(key).get
      if (obj != null) {
        return obj.asInstanceOf[T]
      }
    } catch {
      case e: Exception =>
    }
    default
  }


}
