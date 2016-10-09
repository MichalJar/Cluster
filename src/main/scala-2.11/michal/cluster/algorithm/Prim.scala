package michal.cluster.algorithm

import michal.cluster.model.{Link, Links, Point, Points}

/**
  * Created by michal on 02.10.16.
  */
object Prim {

  type Dist[Data] = (Data, Data) => Double

  def getMST[Data](points: Points[Data], distance: Dist[Data]): Links
  = new FullGraphPrim[Data](points, distance).getMST

  def getMST[Data](pointsA: IndexedSeq[Point[Data]], pointsB: Points[Data], distance: Dist[Data]): Links
  = new BiGraphPrim[Data](pointsA, pointsB, distance).getMST
}
