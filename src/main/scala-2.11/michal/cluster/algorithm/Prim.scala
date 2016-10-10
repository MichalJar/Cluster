package michal.cluster.algorithm

import michal.cluster.model._

/**
  * Created by michal on 02.10.16.
  */
class Prim[Data](subGraphNum: Int, distance: Dist[Data]) {

  def toMSTFrom(association: GraphAssociation,  allPoints: Points[Data]): Links =
    if(association.aGraphIndex == association.bGraphIndex){

      val neededPoints = PointsGetter.getPointsBy(association.aGraphIndex, subGraphNum, allPoints)

      val fullPrim = new FullGraphPrim[Data](neededPoints, distance)

      fullPrim.getMST
    } else {

      val neededPointsA = PointsGetter.getPointsBy(association.aGraphIndex, subGraphNum, allPoints)
      val neededPointsB = PointsGetter.getPointsBy(association.bGraphIndex, subGraphNum, allPoints)

      val biPrim = new BiGraphPrim[Data](neededPointsA, neededPointsB, distance)

      biPrim.getMST
    }
}
