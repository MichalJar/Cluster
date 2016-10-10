package michal.cluster.algorithm

import michal.cluster.model.{Link, Point}
import org.scalatest.FlatSpec

/**
  * Created by michal on 10.10.16.
  */
class BiGraphPrimTest extends FlatSpec {

  private def isThisLink(link: Link, a: Int, b: Int) = link.aId == a && link.bId == b || link.aId == b && link.bId == a

  "BiGraphPrim.toMST" should "return MST as sequence of Links for two sets of Points and fake distance function" in {

    def fakeDistance(a: Int, b: Int): Double = (a, b) match {

      case (0, 5) | (5, 0) => 4.0
      case (0, 6) | (6, 0) => 3.0
      case (0, 7) | (7, 0) => 7.0
      case (1, 5) | (5, 1) => 10.0
      case (1, 9) | (9, 1) => 14.0
      case (2, 6) | (6, 2) => 9.0
      case (2, 8) | (8, 2) => 2.0
      case (3, 9) | (9, 3) => 8.0
      case (4, 9) | (9, 4) => 8.0

      case _ => Double.MaxValue
    }

    val fakePointsA = IndexedSeq(0, 1, 2, 3, 4).map(id => new Point(id, id))

    val fakePointsB = IndexedSeq(5, 6, 7, 8, 9).map(id => new Point(id, id))

    val prim = new BiGraphPrim(fakePointsA, fakePointsB, fakeDistance)

    val mst = prim.getMST

    assert(mst.size === 9)

    assert(mst.find(link => isThisLink(link, 0, 5)).get.distance === 4.0)
    assert(mst.find(link => isThisLink(link, 0, 6)).get.distance === 3.0)
    assert(mst.find(link => isThisLink(link, 0, 7)).get.distance === 7.0)
    assert(mst.find(link => isThisLink(link, 1, 5)).get.distance === 10.0)
    assert(mst.find(link => isThisLink(link, 1, 9)).get.distance === 14.0)
    assert(mst.find(link => isThisLink(link, 2, 6)).get.distance === 9.0)
    assert(mst.find(link => isThisLink(link, 2, 8)).get.distance === 2.0)
    assert(mst.find(link => isThisLink(link, 3, 9)).get.distance === 8.0)
    assert(mst.find(link => isThisLink(link, 4, 9)).get.distance === 8.0)
  }

}
