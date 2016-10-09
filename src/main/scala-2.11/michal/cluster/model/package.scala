package michal.cluster

/**
  * Created by michal on 02.10.16.
  */
package object model {

  type Points[Data] = IndexedSeq[Point[Data]]

  type Links = IndexedSeq[Link]
}
