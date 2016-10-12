package michal.cluster.algorithm

import michal.cluster.model.{Point, Points}
import org.scalatest.FlatSpec

/**
  * Created by michal on 10.10.16.
  */
class PointsGetterTest extends FlatSpec {

  "PointsGetter.getPointBy" should "extract points belonged to set pointed by given set index" in {
    val points: Points[Int] = IndexedSeq(
      new Point(0, 0), // 0
      new Point(1, 0),
      new Point(2, 0),
      new Point(3, 0), // 1
      new Point(4, 0),
      new Point(5, 0),
      new Point(6, 0), // 2
      new Point(7, 0),
      new Point(8, 0),
      new Point(9, 0), // 3
      new Point(10, 0),
      new Point(11, 0),
      new Point(12, 0),// rest : to 0
      new Point(13, 0),// rest : to 1
      new Point(14, 0) // rest : to 2
    )

    val subSetNum = 4

    val set0 = PointsGetter.getPointsBy(0, subSetNum, points)

    assert(set0.size === 4)
    assert(set0(0) === points(0))
    assert(set0(1) === points(1))
    assert(set0(2) === points(2))
    assert(set0(3) === points(12))

    val set1 = PointsGetter.getPointsBy(1, subSetNum, points)

    assert(set1.size === 4)
    assert(set1(0) === points(3))
    assert(set1(1) === points(4))
    assert(set1(2) === points(5))
    assert(set1(3) === points(13))

    val set2 = PointsGetter.getPointsBy(2, subSetNum, points)

    assert(set2.size === 4)
    assert(set2(0) === points(6))
    assert(set2(1) === points(7))
    assert(set2(2) === points(8))
    assert(set2(3) === points(14))

    val set3 = PointsGetter.getPointsBy(3, subSetNum, points)

    assert(set3.size === 3)
    assert(set3(0) === points(9))
    assert(set3(1) === points(10))
    assert(set3(2) === points(11))
  }

}
