package michal.cluster.distribute

import michal.cluster.algorithm.FullGraphPrim
import michal.cluster.model.{Link, Point, Points}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.FlatSpec
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.util.Random

/**
  * Created by michal on 10.10.16.
  */
class HierarchicalClusteringTest extends FlatSpec {


  "HierarchicalClustering.computeMSTLinks" should "for given points set return mst equal by distances sum"+
    " to mst computed by fill prim for this same random set" in {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val sparkConf = new SparkConf().setAppName("Test").setMaster("local")
    val sc = new SparkContext(sparkConf)

    def thisPointsAreLinkedBy(aId: Int, bId: Int, link: Link): Boolean = {
      (link.aId == aId && link.bId == link.bId) || (link.aId == bId && link.bId == aId)
    }


    val points: Points[(Int, Int, Int)] = Array.range(0, 100).map{index =>

      val x = Random.nextInt(1000) - 500
      val y = Random.nextInt(1000) - 500
      val z = Random.nextInt(1000) - 500

      new Point(index, (x, y, z))
    }

    val distance = (a: (Int, Int, Int), b: (Int, Int, Int)) => {
      val x = (a._1 - b._1) * (a._1 - b._1)
      val y = (a._2 - b._2) * (a._2 - b._2)
      val z = (a._3 - b._3) * (a._3 - b._3)

      val root = Math.sqrt(x.toDouble + y.toDouble + z.toDouble)
      root.toInt.toDouble
    }

    val prim = new FullGraphPrim(points, distance)

    val mstLinksClustering = HierarchicalClustering.computeMSTLinks(points, sc, 4, distance)

    val mstLinksFullPrim = prim.getMST.sortBy(l => l.distance)

    assert( mstLinksClustering.map(l => l.distance).sum === mstLinksFullPrim.map(l => l.distance).sum )
  }
}