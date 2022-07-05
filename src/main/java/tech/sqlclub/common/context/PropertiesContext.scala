package tech.sqlclub.common.context

import java.io.{File, FileInputStream, InputStream}
import java.util.Properties

import tech.sqlclub.common.log.Logging
import tech.sqlclub.common.utils.{FileUtils, ParamMapUtils}

object PropertiesContext extends Logging {
  val resourcePath = this.getClass.getClassLoader.getResource("").getPath
  private lazy val properties = new Properties()

  import scala.collection.JavaConversions._
  private lazy val paramMap = {
    getAllPropertyFile.map(loadPropertyFile _ )
    properties.toMap[String, AnyRef]
  }

  def getAllPropertyFile = FileUtils.lsfile(resourcePath,".*\\.properties")

  def loadPropertyFile(file:File)
                    (implicit inputStream:InputStream=new FileInputStream(file)) = {
    try {
      logInfo(s"load properties file: ${file.getPath}")
      properties.load(inputStream)
    } finally {
      inputStream.close()
    }
  }


  implicit class PropertiesContextImpl( _this_ : PropertiesContext.type ) extends ParamMapUtils(_this_.paramMap)

}
