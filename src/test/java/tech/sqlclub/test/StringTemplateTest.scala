package tech.sqlclub.test

import scala.collection.mutable
import tech.sqlclub.common.utils.StringTemplate.namedEvaluate

/**
  * StringTemplate 测试
  * Created by songgr on 2022/05/31.
  */
object StringTemplateTest {

  def main(args: Array[String]): Unit = {
    val s =
      """
        |#if ( $a.split(",")[1] == "jack" )
        |  select * from $a.split(",")[1] as t;
        |#end
        |
        |#foreach( $it in $a.split(",") )
        | select * from $it as t;
        |#end
        | # dedede ${b} $a ${b}a
        | ## dedede
        | ## dedede
        | ## dedede
        | !dede
        | ###
        | #[[ ### ]]#
        | 111
        |#macro( d $name)$name#end
        |#d("#")#d("#")
        |#macro(noescape $c)$c#end
        |
        |#noescape("#")#noescape("#")
                """ stripMargin

    val context = new mutable.HashMap[String, Any]
    context.put("a", "jack,jack")
    context.put("b", "bbbb")


    println(namedEvaluate(s, context.toMap))
  }
}
