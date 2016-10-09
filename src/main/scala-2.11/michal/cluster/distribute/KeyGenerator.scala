package michal.cluster.distribute

import michal.cluster.model.GraphAssociation

/**
  * Created by michal on 09.10.16.
  */
class KeyGenerator(partitionNum: Int, subGraphNum: Int) {

  def getKeyOf(graphAssociation: GraphAssociation): Int = {
    graphAssociation.aGraphIndex * (subGraphNum-1) - ((graphAssociation.aGraphIndex - 1)*graphAssociation.aGraphIndex) / 2 + graphAssociation.bGraphIndex
  }
}
