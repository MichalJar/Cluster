package michal.cluster.distribute

import michal.cluster.model.GraphAssociation
import org.scalatest.FlatSpec

/**
  * Created by michal on 07.10.16.
  */
class KeyGeneratorTest extends FlatSpec {

  "GraphAssociationPartitioner.getPartition" should "return proper partition index from given association" in {

    val graphAssociation0 = new GraphAssociation(0, 0)
    val graphAssociation1 = new GraphAssociation(0, 1)
    val graphAssociation2 = new GraphAssociation(0, 2)
    val graphAssociation3 = new GraphAssociation(1, 1)
    val graphAssociation4 = new GraphAssociation(1, 2)
    val graphAssociation5 = new GraphAssociation(2, 2)

    val keyGenerator = new KeyGenerator(3, 3)

    assert(keyGenerator.getKeyOf(graphAssociation0) === 0)
    assert(keyGenerator.getKeyOf(graphAssociation1) === 1)
    assert(keyGenerator.getKeyOf(graphAssociation2) === 2)
    assert(keyGenerator.getKeyOf(graphAssociation3) === 3)
    assert(keyGenerator.getKeyOf(graphAssociation4) === 4)
    assert(keyGenerator.getKeyOf(graphAssociation5) === 5)
  }

}
