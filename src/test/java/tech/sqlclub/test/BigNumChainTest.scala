package tech.sqlclub.test

import org.scalatest.FunSuite
import tech.sqlclub.common.math.BigNumChain
import tech.sqlclub.common.utils.Assert

/**
  * BigNumChain 测试
  * Created by songgr on 2022/05/31.
  */
class BigNumChainTest extends FunSuite {

  test("BigNumChainTest") {

    val double = BigNumChain.startOf(1.0)
      .add(2.0)
      .subtract(3.0)
      .getDouble

    Assert.isTrue(double == 0, "测试失败")

    println(1.0d + 2.0 - 3.0)

  }

}
