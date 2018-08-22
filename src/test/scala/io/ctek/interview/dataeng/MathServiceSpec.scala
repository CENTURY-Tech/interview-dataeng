package io.ctek.interview.dataeng

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

import scala.util.Success

class MathServiceSpec extends Specification {
  def is: SpecStructure = sequential ^
    s2"""
      MathService
      =============

      gcd
        should return correct result for one value $singleValue
        should return error single value of zero $singleValueZero
        should return correct result for two value $twoValue
        should return correct result for five value $fiveValues
    """

  val mathService = new MathService

  def singleValue = {
    val dataEnv = DataEnvironment(Set(24))
    mathService.gcd.run(dataEnv) must beEqualTo(
      Success(Right(24))
    )
  }
  def singleValueZero = {
    val dataEnv = DataEnvironment(Set(0))
    mathService.gcd.run(dataEnv) must beEqualTo(
      Success(Left("gcd of 0 is undefined"))
    )
  }
  def twoValue = {
    val dataEnv = DataEnvironment(Set(14, 7))
    mathService.gcd.run(dataEnv) must beEqualTo(
      Success(Right(7))
    )
  }
  def fiveValues = {
    val dataEnv = DataEnvironment(Set(25, 35, 75, 145, 90))
    mathService.gcd.run(dataEnv) must beEqualTo(
      Success(Right(5))
    )
  }
}
