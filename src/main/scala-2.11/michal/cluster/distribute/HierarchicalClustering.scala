package michal.cluster.distribute

import michal.cluster.algorithm.{Kruskal, Prim}
import michal.cluster.model._
import org.apache.spark.SparkContext

/**
  * Created by michal on 08.10.16.
  */
object HierarchicalClustering {

  def computeMSTLinks[Data](points: Points[Data], sc: SparkContext, setNum: Int, distance: Dist[Data]): Links = {

    // inside getLinks indexes defined by user for points are replaced by internal indexes for using inside algorithms
    // new indexes are unique incrementing numbers from 0 to points.size - 1
    val internalIndexedPoints = points.zipWithIndex.map{case (point, index) => new Point(index, point.data)}

    val userIndexes = points.map(point => point.index)

    // create sub-set associations
    val setAssociations = Array.range(0, setNum).flatMap(
      first => Array.range(first, setNum).map(second => new SetAssociation(first, second))
    )

    // get default number of partitions created in the computer cluster - for default num of partitions = num of cores
    val partitionNum = sc.defaultParallelism

    // broadcast internal indexed points to all nodes in cluster
    // every node should has full information about data set given to clustering
    val broadcastedAllPoints = sc.broadcast(internalIndexedPoints)

    // create custom partitioner for equal separation of associations to partitions by key
    val partitioner = new AssociationPartitioner(partitionNum)

    // distribute graph associations throw all partitions
    val distributedAssociations = sc.parallelize(setAssociations)
      .keyBy(association => KeyGenerator.getKeyOf(partitionNum, setNum, association))
      .partitionBy(partitioner)

    // compute partial MSTs for every bi-graph or full graph created from sub-graphs pointed by graph associations
    //val prim = new Prim(subSetNum, distance)
    val partialMSTs = distributedAssociations
      .flatMap{case (key, association) => Prim.computeMST(setNum, association, broadcastedAllPoints.value, distance) }

    val collectedMSTs = partialMSTs.collect()

    // filter given links by Kruskal
    val mainMST = Kruskal.computeMST(collectedMSTs.toIndexedSeq, points.size)

    // map all indexes in computed links (internal indexes) to external user indexes
    mainMST.map(link => new Link(userIndexes(link.aId), userIndexes(link.bId), link.distance ) )
  }

}
