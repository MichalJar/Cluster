package michal.cluster.algorithm

import michal.cluster.model._

/**
  * Created by michal on 02.10.16.
  */
object Prim {

  def computeMST[Data](setNum: Int, association: SetAssociation, allPoints: Points[Data], distance: Dist[Data]): Links =
    if(association.aSetIndex == association.bSetIndex){

      val neededPoints = PointsGetter.getPointsBy(association.aSetIndex, setNum, allPoints)

      val fullPrim = new FullGraphPrim[Data](neededPoints, distance)

      fullPrim.getMST
    } else {

      val neededPointsA = PointsGetter.getPointsBy(association.aSetIndex, setNum, allPoints)
      val neededPointsB = PointsGetter.getPointsBy(association.bSetIndex, setNum, allPoints)

      val biPrim = new BiGraphPrim[Data](neededPointsA, neededPointsB, distance)

      biPrim.getMST
    }
}
