package michal.cluster.algorithm

import michal.cluster.model.{Link, Links, Point}
import org.scalatest.FlatSpec

/**
  * Created by michal on 02.10.16.
  */
class PrimTest extends FlatSpec {

  private def isThisLink(link: Link, a: Int, b: Int) = link.aId == a && link.bId == b || link.aId == b && link.bId == a

  "Prim.getMST" should "return MST as sequence of Links for one set of Points and fake distance function" in {

    def fakeDistance(a: Int, b: Int): Double = (a, b) match {
      case (0, 1) | (1, 0) => 17.0
      case (0, 2) | (2, 0) => 16.0
      case (0, 3) | (3, 0) => 15.0  // in MST
      case (2, 3) | (3, 2) => 12.0  // in MST
      case (3, 1) | (1, 3) => 17.0
      case (3, 5) | (5, 3) => 16.0  // in MST
      case (3, 4) | (4, 3) => 18.0
      case (1, 5) | (5, 1) => 9.0   // in MST
      case (4, 5) | (5, 4) => 5.0   // in MST

      case _ => Double.MaxValue
    }

    val fakePoints = IndexedSeq(0, 1, 2, 3, 4, 5).map(id => new Point(id, id))

    val mst = Prim.getMST(fakePoints, fakeDistance)

    assert(mst.size === 5)

    assert(mst.find(link => isThisLink(link, 0, 3) ).get.distance === 15.0)
    assert(mst.find(link => isThisLink(link, 2, 3) ).get.distance === 12.0)
    assert(mst.find(link => isThisLink(link, 3, 5) ).get.distance === 16.0)
    assert(mst.find(link => isThisLink(link, 1, 5) ).get.distance === 9.0)
    assert(mst.find(link => isThisLink(link, 4, 5) ).get.distance === 5.0)
  }

  it should "return MST as sequence of Links for two sets of Points and fake distance function" in {

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

    val mst = Prim.getMST(fakePointsA, fakePointsB, fakeDistance)

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
