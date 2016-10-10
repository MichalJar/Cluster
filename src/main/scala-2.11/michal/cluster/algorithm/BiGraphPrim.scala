package michal.cluster.algorithm

import michal.cluster.model._

/**
  * Created by michal on 04.10.16.
  */
class BiGraphPrim[Data](pointsA: Points[Data], pointsB: Points[Data], distance: Dist[Data]) {

  private val points = pointsA ++ pointsB
  private val distances = Array.fill(points.size)(Double.MaxValue)

  // indexes used by prim are distinct from user indexes and internal indexes
  // but mst links computed by prim use given (probably internal) point indexes
  private val chosenPointIndexes = Array.fill(points.size)(-1)
  private val isLinked = Array.fill(points.size)(false)
  private val links = new Array[Link](points.size - 1)

  private var currentGraphPart = Part.A

  def getMST: Links = {

    var step = 0
    var currentPointIndex = 0

    isLinked(currentPointIndex) = true

    while (step < (points.size - 1)) {

      // update distances and chosenPointIndexes
      update(currentPointIndex)

      currentPointIndex = getBestIndex()

      if(currentPointIndex < pointsA.size) currentGraphPart = Part.A
      else currentGraphPart = Part.B

      isLinked(currentPointIndex) = true

      val sourcePointIndex = chosenPointIndexes(currentPointIndex)

      links(step) = new Link(
        points(currentPointIndex).index,
        points(sourcePointIndex).index,
        distances(currentPointIndex)
      )

      step = step + 1
    }

    links.toIndexedSeq
  }

  def update(currentPointIndex: Int): Unit = {
    var index = currentGraphPart match {
      case Part.A => pointsA.size
      case Part.B => 0
    }

    val maxIndex = currentGraphPart match {
      case Part.A => pointsA.size + pointsB.size
      case Part.B => pointsA.size
    }

    var min = Double.MaxValue

    while (index < maxIndex) {

      if (!isLinked(index)) {
        val newDistance = distance(points(currentPointIndex).data, points(index).data)

        if (newDistance < distances(index)) {
          distances(index) = newDistance
          chosenPointIndexes(index) = currentPointIndex
        }

      }
      index = index + 1
    }
  }

  def getBestIndex(): Int = {
    var min = Double.MaxValue
    var bestIndex = -1

    var index = 0
    while(index < points.size){

      if(! isLinked(index) && min > distances(index)){
        min = distances(index)
        bestIndex = index
      }

      index = index + 1
    }

    bestIndex
  }
}

