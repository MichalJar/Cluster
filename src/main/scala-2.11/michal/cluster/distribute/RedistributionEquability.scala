package michal.cluster.distribute

import michal.cluster.model.SetAssociation

/**
  * Created by michal on 16.10.16.
  */
object RedistributionEquability {
  def getSetNumFor(coreNum: Int, redistributionInequalityPercent: Double): Int = {
    val setNum = 10.0 * Math.sqrt((2 * coreNum).toDouble / redistributionInequalityPercent)
    setNum.toInt + 1
  }
}
