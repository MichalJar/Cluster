package michal.cluster.algorithm

import scala.annotation.tailrec

/**
  * Created by michal on 07.10.16.
  */
class UnionSet(pointNum: Int) {

  val parents = Array.range(0, pointNum)
  val range = Array.fill(parents.size)(1)

  def union(first: Int, second: Int): Boolean = {
    val firstParent = getParent(first)
    val secondParent = getParent(second)

    val mainParent = if(range(firstParent) >= range(secondParent)) {
      range(firstParent) = range(firstParent) + 1
      firstParent
    }
    else {
      range(secondParent) = range(secondParent) + 1
      secondParent
    }

    parents(firstParent) = mainParent
    parents(secondParent) = mainParent

    parents(first) = mainParent
    parents(second) = mainParent

    if(firstParent == secondParent) true
    else false
  }

  def isUnion(first: Int, second: Int): Boolean = {
    val firstParent = getParent(first)
    val secondParent = getParent(second)

    if(firstParent == secondParent) true
    else false
  }

  private def getParent(index: Int): Int = {

    if(parents(index) == index) index
    else {
      val realParent = getParent(parents(index))
      parents(index) = realParent
      realParent
    }
  }

}
