package tech.sqlclub.test

import org.scalatest.FunSuite
import tech.sqlclub.common.context.{PropertiesContext, YamlContext}

/**
  *
  * Created by songgr on 2021/03/11.
  */
class ContextSuite extends FunSuite {

  test("yaml test") {

    val list = YamlContext.getListMap("quartz-job")
    println(list)
    val str = YamlContext.getStringValue("test")
    println(str)
    println(YamlContext.getStringValue("test"))
    println(YamlContext.getStringValue("test"))
    println(YamlContext.getStringValue("test"))
    println(YamlContext.getStringValue("test"))
    println(YamlContext.getStringValue("test"))
  }

  test("properties test") {
    val str = PropertiesContext.getStringValue("a")
    println(str)
  }
}
