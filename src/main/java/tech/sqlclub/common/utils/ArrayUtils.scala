package tech.sqlclub.common.utils

import scala.reflect.ClassTag

/**
  *
  * Created by songgr on 2020/10/24.
  */
object ArrayUtils {

  def equals[T: ClassTag](arr1: Array[T], arr2: Array[T]): Boolean = {
    if (arr1 == arr2) return true
    if (arr1 == null || arr2 == null) return false
    if (arr1.length != arr2.length) return false

    for (i <- 0 until arr1.length) {
      val o1 = arr1(i)
      val o2 = arr2(i)

      if (! (if (o1 == null ) o2 == null else o1.equals(o2)) ) {
        return false
      }
    }
    return true
  }
}
