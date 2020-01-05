package tech.sqlclub.common.context

import java.io.{File, FileInputStream, InputStream}
import java.util.Properties

import tech.sqlclub.common.utils.{FileUtils, ParamMapUtils}

object PropertiesContext {
  val resourcePath = this.getClass.getClassLoader.getResource("").getPath
  lazy val properties = new Properties()

  import scala.collection.JavaConversions._
  private lazy val paramMap = {
    getAllPropertyFile.map(loadPropertyFile _ )
    properties.toMap[String, AnyRef]
  }

  def getAllPropertyFile = FileUtils.lsfile(resourcePath,".*\\.properties")

  def loadPropertyFile(file:File)
                    (implicit inputStream:InputStream=new FileInputStream(file)) = {
    try {
      properties.load(inputStream)
    } finally {
      inputStream.close()
    }
  }


  implicit class PropertiesContext_impl( _this_ : PropertiesContext.type ) extends ParamMapUtils(_this_.paramMap)

}
