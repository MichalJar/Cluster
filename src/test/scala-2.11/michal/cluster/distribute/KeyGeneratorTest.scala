package michal.cluster.distribute

import michal.cluster.model.SetAssociation
import org.scalatest.FlatSpec

/**
  * Created by michal on 07.10.16.
  */
class KeyGeneratorTest extends FlatSpec {

  "GraphAssociationPartitioner.getPartition" should "return proper partition index from given association" in {

    val graphAssociation0 = new SetAssociation(0, 0)
    val graphAssociation1 = new SetAssociation(0, 1)
    val graphAssociation2 = new SetAssociation(0, 2)
    val graphAssociation3 = new SetAssociation(1, 1)
    val graphAssociation4 = new SetAssociation(1, 2)
    val graphAssociation5 = new SetAssociation(2, 2)

    assert(KeyGenerator.getKeyOf(3, 3, graphAssociation0) === 0)
    assert(KeyGenerator.getKeyOf(3, 3, graphAssociation1) === 1)
    assert(KeyGenerator.getKeyOf(3, 3, graphAssociation2) === 2)
    assert(KeyGenerator.getKeyOf(3, 3, graphAssociation3) === 3)
    assert(KeyGenerator.getKeyOf(3, 3, graphAssociation4) === 4)
    assert(KeyGenerator.getKeyOf(3, 3,graphAssociation5) === 5)
  }

}
