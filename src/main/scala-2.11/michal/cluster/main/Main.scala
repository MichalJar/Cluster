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
    //println("num of arg: " + args.size)
    //println("args:")
    args.foreach(arg =>  println("\t" + arg))
    val pointsFileName = args(0)
    //println("points file name: " + pointsFileName)
    val points = getPointsFrom(pointsFileName)

    val distance: Dist[Position] = (pA: Position, pB: Position) => {
      val x = (pA.x - pB.x) * (pA.x - pB.x)
      val y = (pA.y - pB.y) * (pA.y - pB.y)
      Math.sqrt(x + y)
    }

    val context = new SparkContext()

    val links = HierarchicalClustering.computeLinks(points, context, 1.0, distance)

    saveToFile("links.csv", links)
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
    val string: String = links.map(link => link.aId + "," + link.bId + "," + link.distance).reduce(_ + "\n" + _)

    val file: File = new File(fileName)

    val writer = new PrintWriter(file)

    writer.write(string)

    writer.close()
  }
}
