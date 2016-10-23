package michal.cluster.algorithm

import michal.cluster.model.{Link, Points}

import scala.annotation.tailrec

/**
  * Created by michal on 02.10.16.
  */
object Kruskal {


  // indexes of point must be indexes from 0 to points.size - 1
  def computeMST[Data](links: Array[Link], pointNum: Int): IndexedSeq[Link] = {

    val mstLinks = new Array[Link](pointNum - 1)

    val unionSet = new UnionSet(pointNum)

    val sortedLinks = links.sortBy(link => link.distance)

    val indexes = Array.range(0, pointNum)

    var step = 0
    var mstNum = 0
    while(step < sortedLinks.size && mstNum < (pointNum - 1)){

      val link = sortedLinks(step)
      if(! unionSet.isUnion(link.aId, link.bId)){
        mstLinks(mstNum) = link
        unionSet.union(link.aId, link.bId)
        mstNum = mstNum + 1
      }

      step = step + 1
    }

    mstLinks.toIndexedSeq
  }





}
