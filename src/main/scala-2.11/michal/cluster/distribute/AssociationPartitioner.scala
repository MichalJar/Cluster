package michal.cluster.distribute

import michal.cluster.model.SetAssociation
import org.apache.spark.Partitioner

/**
  * Created by michal on 06.10.16.
  */
class AssociationPartitioner(partitionNum: Int, setNum: Int) extends Partitioner {

  override def numPartitions: Int = partitionNum

  override def getPartition(key: Any): Int = {
    val association = key.asInstanceOf[SetAssociation]
    if(association.aSetIndex == association.bSetIndex){
      partitionNum - 1 - (setNum - association.aSetIndex - 1) % partitionNum
    }
    else{
      val i = ((2*setNum-association.aSetIndex+1)*association.aSetIndex) / 2 - 2*association.aSetIndex + association.bSetIndex - 1
      i % partitionNum
    }
  }
}
