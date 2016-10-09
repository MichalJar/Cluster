package michal.cluster.distribute

import michal.cluster.model.{GraphAssociation, Links, Points}

/**
  * Created by michal on 09.10.16.
  */
class PartMST[Data](subGraphNum: Int){


  def toPartMST(graphAssociation: GraphAssociation, points: Points[Data]): Links = {
        ???
  }

  private def getPointsBy(subGraphIndex: Int, points: Points[Data]): Points[Data] = {
    val subGraphSize = points.size / subGraphNum

    val offset = subGraphIndex * subGraphSize

    val rest = subGraphIndex % subGraphNum

    val mainPoints = points.slice(offset, offset + subGraphSize)

    if(rest > offset){
      val additionalPoint = subGraphSize * subGraphNum + subGraphIndex
    }
    else mainPoints
  }

}
