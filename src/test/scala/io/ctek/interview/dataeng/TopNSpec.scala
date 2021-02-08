package io.ctek.interview.dataeng

import org.specs2.Specification
import org.specs2.specification.core.SpecStructure

class TopNSpec extends Specification {
  def is: SpecStructure = sequential ^
    s2"""
      TopN
      =============

      findTop
        should return correct result for no value $noValue
        should return correct result for one value $oneValue
        should return correct result for three values $threeValues
        should return correct result for ten values $tenValues
    """

  val topN = new TopN

  def noValue = {
    topN.findTopN(3)(LazyList.empty) must beEqualTo(
      List.empty
    )
  }
  def oneValue = {
    topN.findTopN(3)(LazyList(1)) must beEqualTo(
      List(1)
    )
  }
  def threeValues = {
    topN.findTopN(3)(LazyList(23, 589, 90)) must beEqualTo(
      List(589, 90, 23)
    )
  }
  def tenValues = {
    topN.findTopN(3)(LazyList(133, 835, 295, 806, 364, 666, 717, 732, 835, 411)) must beEqualTo(
      List(835, 806, 732)
    )
  }
}