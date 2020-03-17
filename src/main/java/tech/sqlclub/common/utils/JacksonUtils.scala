package tech.sqlclub.common.utils

import com.fasterxml.jackson.annotation.JsonInclude.Include

import scala.util.control.NonFatal
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import tech.sqlclub.common.log.Logging

object JacksonUtils extends Logging {
  private val _mapper = new ObjectMapper()
  _mapper.registerModule(DefaultScalaModule)


  private lazy val mapper = {
    val objectMapper = new ObjectMapper with ScalaObjectMapper
    objectMapper.setSerializationInclusion(Include.NON_NULL)
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper
  }

  def toJson[T](obj: T): String = {
    _mapper.writeValueAsString(obj)
  }

  def fromJson[T](json: String, `class`: Class[T]): T = {
    try {
      _mapper.readValue(json, `class`)
    } catch {
      case NonFatal(e) =>
        logError(e.getMessage, e)
        null.asInstanceOf[T]
    }
  }

  def fromJson[T: Manifest](json: String): T = {
    mapper.readValue[T](json)
  }

  def prettyPrint[T](obj: T): String = {
    _mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)
  }

}
