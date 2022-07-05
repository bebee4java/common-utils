package tech.sqlclub.common.utils

/**
  * 版本处理工具
  * Created by songgr on 2022/02/18.
  */
object VersionUtils {

  /**
    * 版本比较工具
    * 如果 `version1 > version2` 返回 1，如果 `version1 < version2` 返回 -1，`version1 = version2` 返回 0
    * @param version1
    * @param version2
    * @return
    */
  def compareVersion(version1: String, version2: String): Int = {
    val nums1 = version1.split("\\.")
    val nums2 = version2.split("\\.")
    val n1 = nums1.length
    val n2 = nums2.length
    for (i <- 0 until Math.max(n1, n2)) {
      val num1 = if (i < n1) nums1(i).toInt else 0
      val num2 = if (i < n2) nums2(i).toInt else 0
      if (num1 != num2)
        return if (num1 < num2) -1 else 1
    }
    0
  }

}
