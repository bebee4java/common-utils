package tech.sqlclub.common.regex


object RegexOp {
  implicit class RegexString(s:String) {
    /**
      * 判断字符串是否匹配正则
      * @param pattern 正则表达式
      * @return Boolean
      */
    def matching(pattern:String):Boolean = (pattern.r findFirstIn s).isDefined


    /**
      * 正则匹配按组获取值
      * @param pattern 正则表达式
      * @param group 第几组
      * @return Iterator[String]
      */
    def matchGroup(pattern:String, group:Int):Iterator[String] = {
      val matchs = pattern.r findAllMatchIn s
      matchs.map(_.group(group))
    }

    /**
      * 正则匹配所有值
      * @param pattern 正则表达式
      * @return List[String]
      */
    def matchAll(pattern:String):List[String] = (pattern.r findAllIn s).toList

    /**
      * 正则匹配第一个值
      * @param pattern 正则表达式
      * @return Option[String]
      */
    def matchFirst(pattern:String):Option[String] = pattern.r findFirstIn s


    /**
      * 判断字符串是否是数值格式
      * @return Boolean
      */
    def isMumeric:Boolean = s matching "^(\\+|\\-)?(0+\\.[\\d]+|[1-9]+[\\d]*\\.[\\d]+|[\\d]+)$"


    /**
      * 提取字符串中所有的数值
      * @return List[String]
      */
    def extractMumeric:List[String] = s matchAll "(\\+|\\-)?(0+\\.[\\d]+|[1-9]+[\\d]*\\.[\\d]+|[\\d]+)"

    /**
      * 判断字符串是否是手机号格式
      * @param len 字符串长度
      * @return Boolean
      */
    def isMobileNumber(implicit len:Int = s.length):Boolean = {
      val mobileNumberRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))[0-9]{8}$"
      // 截取后11位
      val number = s.slice(len-11, len)
      number matching mobileNumberRegex
    }

    /**
      * 提取字符串所有手机号
      * @return List[String]
      */
    def extractMobileNumber:List[String] = s matchAll "((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))[0-9]{8}"

  }

}


