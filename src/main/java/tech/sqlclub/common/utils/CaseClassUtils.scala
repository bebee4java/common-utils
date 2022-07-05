package tech.sqlclub.common.utils

/**
  *
  * Created by songgr on 2021/01/04.
  */
object CaseClassUtils {

  /**
    * 返回caseClass所有字段信息（字段名称, 字段类型, 字段值）
    * @param c 指定caseClass
    * @return List[CaseClass]
    */
  def caseClassFields(c: AnyRef): List[CaseClass] = {
    val fields = (List[CaseClass]() /: c.getClass.getDeclaredFields) { (res, f) =>
      f.setAccessible(true)
      res ++ List(CaseClass(f.getName, f.getType, f.get(c)))
    }
    fields
  }

  case class CaseClass(fieldName:String, fieldType:Class[_], fieldValue:Any)

  val JByte = classOf[java.lang.Byte]
  val JShort = classOf[java.lang.Short]
  val JChar = classOf[java.lang.Character]
  val JInteger = classOf[java.lang.Integer]
  val JLong = classOf[java.lang.Long]
  val JFloat = classOf[java.lang.Float]
  val JDouble = classOf[java.lang.Double]
  val JString = classOf[java.lang.String]
  val JBoolean = classOf[java.lang.Boolean]

  val SByte = classOf[Byte]
  val SShort = classOf[Short]
  val SChar = classOf[Char]
  val SInt = classOf[Int]
  val SLong = classOf[Long]
  val SFloat = classOf[Float]
  val SDouble = classOf[Double]
  val SBoolean = classOf[Boolean]
}
