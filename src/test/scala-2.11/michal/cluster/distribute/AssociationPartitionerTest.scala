package michal.cluster.distribute

import michal.cluster.model.SetAssociation
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FlatSpec

/**
  * Created by michal on 15.10.16.
  */
class AssociationPartitionerTest extends FlatSpec{

  "AssociationPartitioner.getPartition" should "return proper index of partition for given association" in {
    val association0 = new SetAssociation(0, 0)   // 1
    val association1 = new SetAssociation(0, 1)     // 0
    val association2 = new SetAssociation(0, 2)     // 1
    val association3 = new SetAssociation(0, 3)     // 2
    val association4 = new SetAssociation(0, 4)     // 0
    val association5 = new SetAssociation(1, 1)   // 2
    val association6 = new SetAssociation(1, 2)     // 1
    val association7 = new SetAssociation(1, 3)     // 2
    val association8 = new SetAssociation(1, 4)     // 0
    val association9 = new SetAssociation(2, 2)   // 0
    val association10 = new SetAssociation(2, 3)    // 1
    val association11 = new SetAssociation(2, 4)    // 2
    val association12 = new SetAssociation(3, 3)  // 1
    val association13 = new SetAssociation(3, 4)    // 0
    val association14 = new SetAssociation(4, 4)  // 2

    val partitioner = new AssociationPartitioner(3, 5)

    assert(partitioner.getPartition(association0) === 1)
    assert(partitioner.getPartition(association1) === 0)
    assert(partitioner.getPartition(association2) === 1)
    assert(partitioner.getPartition(association3) === 2)
    assert(partitioner.getPartition(association4) === 0)
    assert(partitioner.getPartition(association5) === 2)
    assert(partitioner.getPartition(association6) === 1)
    assert(partitioner.getPartition(association7) === 2)
    assert(partitioner.getPartition(association8) === 0)
    assert(partitioner.getPartition(association9) === 0)
    assert(partitioner.getPartition(association10) === 1)
    assert(partitioner.getPartition(association11) === 2)
    assert(partitioner.getPartition(association12) === 1)
    assert(partitioner.getPartition(association13) === 0)
    assert(partitioner.getPartition(association14) === 2)
  }



  "AssociationPartitioner" should "equaly redistribute given associations to partitions" in {
    val coreNum = 5

    val setNum = RedistributionEquability.getSetNumFor(coreNum, 1.0)

    val associations = SetAssociation(setNum)

    val partitioner = new AssociationPartitioner(5, setNum)


    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val sparkConf = new SparkConf().setAppName("Test").setMaster("local[4]")
    val sc = new SparkContext(sparkConf)

    val r = sc.parallelize(associations).keyBy(association => association).partitionBy(partitioner)

    val c = r.map{case (index, association) => if(association.aSetIndex == association.bSetIndex) 1 else 2}

    val part = c.mapPartitions(iterator => Iterator(iterator.toIndexedSeq.sum), preservesPartitioning = true)

    val min = part.min

    val max = part.max

    assert((max - min) <= 2)

    sc.stop()
  }



}
