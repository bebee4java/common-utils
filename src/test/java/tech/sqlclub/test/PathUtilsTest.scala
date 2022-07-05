package tech.sqlclub.test

import tech.sqlclub.common.utils.PathUtils

/**
  *
  * Created by songgr on 2021/02/15.
  */
object PathUtilsTest {

  def main(args: Array[String]): Unit = {

    println(PathUtils("/a/") / "/ada/dede" / "/de/" / null)

    println(PathUtils() / "a" / "a")
  }

}
