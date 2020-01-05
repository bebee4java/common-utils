package tech.sqlclub.common.context

import java.io.{File, FileInputStream, InputStream}
import java.util

import org.yaml.snakeyaml.Yaml
import tech.sqlclub.common.utils.{FileUtils, ParamMapUtils}


object YamlContext {
  val resourcePath = this.getClass.getClassLoader.getResource("").getPath
  lazy val yaml = new Yaml()

  import scala.collection.JavaConversions._
  private lazy val paramMap = getAllYamlFile.flatMap(loadYamlFile[util.Map[String,Object]](_)).toMap

  def getAllYamlFile = FileUtils.lsfile(resourcePath,".*\\.(yaml|yml)")

  def loadYamlFile[A](file:File)
      (implicit inputStream:InputStream=new FileInputStream(file)):A = {
    try {
      yaml.load[A](inputStream)
    } finally {
      inputStream.close()
    }
  }

  implicit class YamlContext_impl( _this_ : YamlContext.type ) extends ParamMapUtils(_this_.paramMap)
}
