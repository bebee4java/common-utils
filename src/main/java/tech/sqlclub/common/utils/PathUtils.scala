package tech.sqlclub.common.utils

import java.io.File
import scala.collection.mutable.ArrayBuffer
import PathUtils.pathSeparator

/**
  * 目录路径处理工具
  * 返回绝对正确的目录路径，不会出现`/a//b/`这种问题
  * Created by songgr on 2021/02/15.
  */
class PathUtils(rootPath:String) {

  require(rootPath != null && rootPath.startsWith(pathSeparator), "root path must not be null and startWith " + pathSeparator)

  private val buffer = new ArrayBuffer[String]()
  buffer += rootPath.trim.stripSuffix(pathSeparator)


  def add(path:String):PathUtils = {
    if (path == null) return this
    val pathStripped = path.trim.stripPrefix(pathSeparator).stripSuffix(pathSeparator)
    if (pathStripped.nonEmpty) {
      buffer += pathStripped
    }
    this
  }

  def / (path:String):PathUtils = {
    add(path)
  }

  def toPath = {
    val path = buffer.mkString(pathSeparator)
    buffer.clear()
    path
  }

  def pathLevel = buffer.size

  override def toString: String = toPath

}

object PathUtils {

  val pathSeparator: String = File.separator

  def apply(rootPath: String = pathSeparator): PathUtils = new PathUtils(rootPath)

}
