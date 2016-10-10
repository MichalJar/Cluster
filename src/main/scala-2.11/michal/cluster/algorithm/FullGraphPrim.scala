package michal.cluster.algorithm

import michal.cluster.model.{Dist, Link, Links, Points}

/**
  * Created by michal on 04.10.16.
  */
class FullGraphPrim[Data](points: Points[Data], distance: Dist[Data]) {

  private val distances = Array.fill(points.size)(Double.MaxValue)
  private val chosenPointIndexes = Array.fill(points.size)(-1)
  private val isLinked = Array.fill(points.size)(false)
  private val links = new Array[Link](points.size - 1)

  def getMST: Links = {

    var step = 0
    var currentPointIndex = 0

    isLinked(currentPointIndex) = true

    while (step < (points.size - 1)) {

      val nearestPointIndex = getBestMinIndex(currentPointIndex)

      isLinked(nearestPointIndex) = true
      val chosenLinkedPointIndex = chosenPointIndexes(nearestPointIndex)
      links(step) = new Link(
        points(chosenLinkedPointIndex).index, points(nearestPointIndex).index, distances(nearestPointIndex))

      currentPointIndex = nearestPointIndex
      step = step + 1
    }

    links.toIndexedSeq
  }

  private def getBestMinIndex(currentPointIndex: Int): Int = {
    var index = 0
    var bestIndex = -1

    var bestMinDistance = Double.MaxValue

    while (index < points.size) {

      if (!isLinked(index)) {
        val newDistance = distance(points(currentPointIndex).data, points(index).data)

        if (newDistance < distances(index)) {
          distances(index) = newDistance
          chosenPointIndexes(index) = currentPointIndex
        }

        if (distances(index) < bestMinDistance) {
          bestMinDistance = distances(index)
          bestIndex = index
        }

      }
      index = index + 1
    }
    bestIndex
  }
}