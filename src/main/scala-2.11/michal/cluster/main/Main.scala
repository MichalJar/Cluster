package michal.cluster.main
import java.io.{File, PrintWriter}

import michal.cluster.distribute.HierarchicalClustering
import michal.cluster.model.{Dist, Links, Point, Points}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source
/**
  * Created by michal on 02.10.16.
  */
object Main extends App{


  class Position(val x: Double, val y: Double) extends Serializable
  override def main(args: Array[String]): Unit = {
    val pointsFileName = args(0)
    println("APP-INFO : points file name: " + pointsFileName)
    val points = getPointsFrom(pointsFileName)

    val distance: Dist[Position] = (pA: Position, pB: Position) => {
      val x = (pA.x - pB.x) * (pA.x - pB.x)
      val y = (pA.y - pB.y) * (pA.y - pB.y)
      Math.sqrt(x + y)
    }

    val context = new SparkContext()
    context.clearJobGroup()

    val links = HierarchicalClustering.computeLinks(points, context, 1.0, distance)

    println("APP-INFO : preparing for write computed links to file links.csv")
    saveToFile("links.csv", links)
    println("APP-INFO : links has been succefully writted to links.csv")

    println("APP-INFO : preparing for closing spark context")
    context.stop()
    println("APP-INFO : spark context has been closed")
  }

  private def getPointsFrom(pointsFileName: String): Points[Position] = {

    val source = Source.fromFile(pointsFileName)

    val lines = source.getLines().toIndexedSeq
    val points = lines
      .map(line => line.split(','))
      .map {case Array(id, x, y) => new Point(id.toInt, new Position(x.toDouble, y.toDouble)) }

    source.close
    points
  }

  private def saveToFile(fileName: String, links: Links): Unit = {
    val strings = links.map(link => link.aId + "," + link.bId + "," + link.distance + "\n")

    val file: File = new File(fileName)

    val writer = new PrintWriter(file)

    strings.foreach(string => writer.write(string))

    writer.close()
  }
}
