package michal.cluster.distribute

import michal.cluster.model.SetAssociation

/**
  * Created by michal on 09.10.16.
  */
object KeyGenerator {

  def getKeyOf(partitionNum: Int, setNum: Int, association: SetAssociation): Int = {
    association.aSetIndex * (setNum-1) - ((association.aSetIndex - 1)*association.aSetIndex) / 2 + association.bSetIndex
  }
}
