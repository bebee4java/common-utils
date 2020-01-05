package tech.sqlclub.common.utils

import java.io.{File, FileInputStream, InputStream}
import java.util.Properties

import org.apache.commons.lang3.StringUtils
import org.yaml.snakeyaml.Yaml
import tech.sqlclub.common.regex.RegexOp._
import scala.collection.JavaConversions._

/**
  *
  * Created by songgr on 2020/01/03.
  */
object FileUtils {

  /**
    * 获取目录文件列表
    * @param path 目录
    * @param pattern 匹配正则
    * @param recursive 是否递归子目录
    * @return Array[File]
    */
  def lsfile(path:String, pattern:String = ".*", recursive:Boolean=false):Array[File] = {

    val file = new File(path)

    if (file.isDirectory){

      val files = file.listFiles().filter(_.isFile).filter(_.getName matching pattern )

      if (recursive) {
        val fs = file.listFiles().filter(_.isDirectory).flatMap{
          f =>
            lsfile(f.getAbsolutePath, pattern, recursive)
        }
        return files ++ fs
      }
      return files
    }
    Array.empty[File]
  }


  /**
    * 创建目录
    * @param path 目录
    * @return Boolean
    */
  def mkdirs(path:String): Boolean = {
    if (StringUtils.isNotBlank(path)) {
      val file = new File(path)
      if (!file.isDirectory){
        if (!file.exists) file.mkdirs()
      }
    }
    false
  }

  def readYamlFile(path:String):Map[String,Object] = readYamlFile(new File(path))

  def readYamlFile(file:File)
    (implicit inputStream:InputStream=new FileInputStream(file)):Map[String,Object] = {
    try {
      val yaml = new Yaml()
      yaml.load[java.util.Map[String,Object]](inputStream).toMap
    } finally {
      inputStream.close()
    }
  }


  def readPropertiesFile(path:String):Map[String,Object] = readPropertiesFile(new File(path))

  def readPropertiesFile(file:File)
    (implicit inputStream:InputStream=new FileInputStream(file)):Map[String,Object] = {
    try {
      val properties = new Properties()
      properties.load(inputStream)
      properties.toMap[String,Object]
    } finally {
      inputStream.close()

    }
  }

}
