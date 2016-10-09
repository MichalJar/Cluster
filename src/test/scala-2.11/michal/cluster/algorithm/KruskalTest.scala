package michal.cluster.algorithm

import michal.cluster.model.Link
import org.scalatest.FlatSpec

import scala.util.Random

/**
  * Created by michal on 02.10.16.
  */
class KruskalTest extends FlatSpec {

  "Kruskal.getMST" should "compute mst for given sequence of Links" in {

    val links = IndexedSeq(
      // MST
      new Link(0, 1, 3),
      new Link(1, 2, 4),
      new Link(1, 3, 6),
      new Link(1, 4, 7),
      new Link(4, 5, 8),
      new Link(4, 6, 11),
      new Link(5, 7, 2),
      // no MST
      new Link(0, 2, 5),
      new Link(0, 7, 7),
      new Link(2, 3, 11),
      new Link(3, 4, 8),
      new Link(1, 5, 9),
      new Link(6, 7, 9)
    )

    val mst = Kruskal.getMST( Random.shuffle(links), 8)

    assert(mst.size === 7)

    assert(mst.contains( mst(0) ))
    assert(mst.contains( mst(1) ))
    assert(mst.contains( mst(2) ))
    assert(mst.contains( mst(3) ))
    assert(mst.contains( mst(4) ))
    assert(mst.contains( mst(5) ))
    assert(mst.contains( mst(6) ))
  }

}
