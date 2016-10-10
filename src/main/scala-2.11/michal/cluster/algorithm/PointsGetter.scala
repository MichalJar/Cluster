package michal.cluster.algorithm

import michal.cluster.model.Points

/**
  * Created by michal on 10.10.16.
  */
object PointsGetter {

  def getPointsBy[Data](subGraphIndex: Int, subGraphNum: Int, allPoints: Points[Data]): Points[Data] = {
    val subGraphSize = allPoints.size / subGraphNum

    val offset = subGraphIndex * subGraphSize

    val rest = subGraphIndex % subGraphNum

    val mainPoints = allPoints.slice(offset, offset + subGraphSize)

    if(rest > subGraphIndex){
      val additionalPointIndex = subGraphSize * subGraphNum + subGraphIndex
      mainPoints :+ allPoints(subGraphIndex)
    }
    else mainPoints
  }

}
