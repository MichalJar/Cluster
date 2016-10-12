package michal.cluster.distribute

import michal.cluster.model.SetAssociation
import org.apache.spark.Partitioner

/**
  * Created by michal on 06.10.16.
  */
class AssociationPartitioner(partitionNum: Int) extends Partitioner {

  override def numPartitions: Int = partitionNum

  override def getPartition(key: Any): Int = {
    val intKey = key.asInstanceOf[Int]
    intKey % partitionNum
  }
}
