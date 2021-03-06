package michal.cluster.distribute

import michal.cluster.algorithm.{Kruskal, Prim}
import michal.cluster.model._
import org.apache.spark.SparkContext

/**
  * Created by michal on 08.10.16.
  */
object HierarchicalClustering {

  def computeLinks[Data](points: Points[Data], sc: SparkContext, redistributionInequalityPercent: Double, distance: Dist[Data]): Links = {

    // inside computeLinks indexes defined by user for points are replaced by internal indexes for using inside algorithms
    // new indexes are unique incrementing numbers from 0 to points.size - 1
    val internalIndexedPoints = points.zipWithIndex.map{case (point, index) => new Point(index, point.data)}

    val userIndexes = points.map(point => point.index)

    // get default number of partitions created in the computer cluster - for default num of partitions = num of cores
    val coreNum = sc.defaultParallelism

    val distributedPartStartTime = System.currentTimeMillis()

    // compute set num needed for full-filling redistributionInequalityPercent constraint
    val setNum = RedistributionEquability.getSetNumFor(coreNum, redistributionInequalityPercent)

    // create sub-set associations
    val setAssociations = SetAssociation(setNum)

    // broadcast internal indexed points to all nodes in cluster
    // every node should has full information about data set given to clustering
    val broadcastedAllPoints = sc.broadcast(internalIndexedPoints)

    // create custom partitioner for equal separation of associations to partitions by key
    val partitioner = new AssociationPartitioner(coreNum, setNum)

    // distribute graph associations throw all partitions
    val distributedAssociations = sc.parallelize(setAssociations)
      .keyBy(association => association)
      .partitionBy(partitioner)

    // compute partial MSTs for every bi-graph or full graph created from sub-graphs pointed by graph associations
    //val prim = new Prim(subSetNum, distance)
    val partialMSTs = distributedAssociations
      .flatMap{case (key, association) => Prim.computeMST(setNum, association, broadcastedAllPoints.value, distance) }
      .cache()

    val collectedMSTs = partialMSTs.collect()
    val distributedComputingTime = System.currentTimeMillis() - distributedPartStartTime
    println("APP-INFO : links are computed in time [ms]: " + distributedComputingTime)
    println("APP-INFO : links are collected - num of generated links: " + collectedMSTs.size)

    val localComputingStartTime = System.currentTimeMillis()
    // filter given links by Kruskal
    val mainMST = Kruskal.computeMST(collectedMSTs, points.size)

    val localComputingTime = System.currentTimeMillis() - localComputingStartTime
    println("APP-INFO : main mst is computed in time [ms]: " + localComputingTime)

    // map all indexes in computed links (internal indexes) to external user indexes
    val mstWithUserIndexes = mainMST.map(link => new Link(userIndexes(link.aId), userIndexes(link.bId), link.distance ) )

    println("APP-INFO : links indexes mapped to external user indexes")

    mstWithUserIndexes.toIndexedSeq
  }

}
