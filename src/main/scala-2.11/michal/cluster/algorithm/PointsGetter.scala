package michal.cluster.algorithm

import michal.cluster.model.Points

/**
  * Created by michal on 10.10.16.
  */
object PointsGetter {

  def getPointsBy[Data](pointSetIndex: Int, setsNum: Int, allPoints: Points[Data]): Points[Data] = {

    val subSetSize = allPoints.size / setsNum

    val offset = pointSetIndex * subSetSize

    val rest = allPoints.size % setsNum

    val mainPoints = allPoints.slice(offset, offset + subSetSize)

    if(rest > pointSetIndex){
      val additionalPointIndex = subSetSize * setsNum + pointSetIndex
      mainPoints :+ allPoints(additionalPointIndex)
    }
    else mainPoints
  }

}
