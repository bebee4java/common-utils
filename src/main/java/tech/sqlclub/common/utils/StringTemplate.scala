package tech.sqlclub.common.utils

import java.io.{StringReader, StringWriter}
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity

/**
  * String 模板处理
  * Created by songgr on 2021/01/19.
  */
object StringTemplate {

  // 也可以使用 #[[ ## ]]# 进行原样输出
  val NOESCAPE = "#macro(noescape $str)$str#end"

  /**
    * 通过 $变量 or ${变量} 对string模板进行命名参数渲染
    * @param templateStr string模板
    * @param parameters 命名参数
    * @param noescapeStrList 不转义的字符列表
    * @return string
    */
  def namedEvaluate(templateStr: String, parameters: Map[String, Any], noescapeStrList: List[String] = List()) = {
    val context: VelocityContext = new VelocityContext
    parameters.foreach{ f =>
      context.put(f._1, f._2)
    }
    val builder = new StringBuilder(templateStr)
    if (noescapeStrList.nonEmpty) {
      var str = templateStr
      for (noescapeStr <- noescapeStrList) {
        str = str.replaceAll(noescapeStr, s"#noescape('$noescapeStr')")
      }
      builder.setLength(0)
      builder.append(str).append(NOESCAPE)
    }
    val w: StringWriter = new StringWriter

    Velocity.evaluate(context, w, "[#StringTemplate#Velocity]", builder.result())
    w.toString
  }

}
