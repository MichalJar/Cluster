package michal.cluster.algorithm

import michal.cluster.model.{Link, Point}
import org.scalatest.FlatSpec

/**
  * Created by michal on 10.10.16.
  */
class FullGraphPrimTest extends FlatSpec {

  private def isThisLink(link: Link, a: Int, b: Int) = link.aId == a && link.bId == b || link.aId == b && link.bId == a

  "FullGraphPrim.getMST" should "return MST as sequence of Links for one set of Points and fake distance function" in {

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

    val prim = new FullGraphPrim(fakePoints, fakeDistance)

    val mst = prim.getMST

    assert(mst.size === 5)

    assert(mst.find(link => isThisLink(link, 0, 3) ).get.distance === 15.0)
    assert(mst.find(link => isThisLink(link, 2, 3) ).get.distance === 12.0)
    assert(mst.find(link => isThisLink(link, 3, 5) ).get.distance === 16.0)
    assert(mst.find(link => isThisLink(link, 1, 5) ).get.distance === 9.0)
    assert(mst.find(link => isThisLink(link, 4, 5) ).get.distance === 5.0)
  }
}
