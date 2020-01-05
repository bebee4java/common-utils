package tech.sqlclub.test

import java.util
import java.util.{Date, Properties}

import com.typesafe.config.ConfigFactory
import org.scalatest.FunSuite
import tech.sqlclub.common.context.YamlContext
import tech.sqlclub.common.net.NetUtils
import tech.sqlclub.common.utils.{FileUtils, ParamMapUtils, TimeUtils}

class CommonSuite extends FunSuite {

  test("config properties") {

    val properties = new Properties()

    properties.setProperty("redis.msg.piper.class", "classA")
    properties.setProperty("redis.msg.maxlength", "100")
    properties.setProperty("redis.database", "4")

    val conf = ConfigFactory.parseProperties(properties)
    println(conf.getInt("redis.database"))

    assert(conf.getInt("redis.database") == 4)
  }

  test("config map") {

    val list = new util.ArrayList[String]()
    list.add("127.0.0.1:6379")

    val _map = Map[String,Object]("redis.msg.piper.class" -> "classA",
      "redis.msg.maxlength" -> "100",
      "redis.database" -> "4",
      "redis.addresses" -> list
    )
    import scala.collection.JavaConversions._
    val conf = ConfigFactory.parseMap(_map)

    println(conf.getInt("redis.database"))

    val entry = conf.entrySet()

    val keys = entry.map(map => map.getKey)
    println(keys)


    assert(conf.getInt("redis.database") == 4)

  }


  test("time util") {
    println(TimeUtils.currentLocaltime)
    println(TimeUtils.dateFormat(new Date()))

    println(TimeUtils.currentLocalDate)
    println(TimeUtils.currentTimestamp)
    println(TimeUtils.dateDiff("2020-01-02 12:00:00", "2020-01-02 13:00:00"))

  }

  test("regex") {
    import tech.sqlclub.common.regex.RegexOp._

    println("aaasdds" matching ".+" )

    println("aaa" matching "a.*") //true

    println("aaa" matching "b.*") //false

    println("a123".matchGroup("(a)(\\d+)", 1).toList ) //List(a)

    println("a123".matchGroup("(a)(\\d+)", 2).toList ) //List(123)

    println("a1a1a" matchAll "a") //List(a, a, a)

    println("a1a1a" matchFirst  "a") //Some(a)

    println("13811111111" isMobileNumber ) //true

    println(
      """
        |13811111111
        |电话:
        |18222222222
      """.stripMargin extractMobileNumber ) //List(13811111111, 18222222222)

    println("-1.0" isMumeric) //true
    println(
      """
        |这是一段文本
        |-1 and -3.0
        |大于 100 +1000
      """.stripMargin extractMumeric) // List(-1, -3.0, 100, +1000)

  }

  test("file utils") {
    FileUtils.mkdirs("/tmp/sgr/test")

    println(FileUtils.lsfile("/tmp/sgr").mkString("\n"))
    println("----------------")
    println(FileUtils.lsfile("/tmp/sgr",recursive = true).mkString("\n"))

    println("----------------")
    println(FileUtils.lsfile("/tmp/sgr",pattern=".*.sh",recursive = true).mkString("\n"))

  }

  test("yaml context") {
//    YamlContext.paramMap.foreach(kv => println(kv._1, kv._2))
  }


  test("ParamMapUtils"){
    val s = ParamMapUtils().getStringValue("test", paramMap = Map("test"-> "aa") )
    println(s)

    val map = Map[String,Object]("test"->"bb")
    val ss = new ParamMapUtils(map).getStringValue("test" )
    println(ss)

  }

  test("net utils") {
    println(NetUtils.getLocalServerIp)
    println(NetUtils.getLocalServerIpv4List)
    println(NetUtils.portAvailableAndReturn("127.0.0.1", 2000, 9999))
  }

}
