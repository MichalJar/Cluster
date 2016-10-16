package michal.cluster.model

/**
  * Created by michal on 06.10.16.
  */
class SetAssociation(val aSetIndex: Int, val bSetIndex: Int) extends Serializable

object SetAssociation {
  def apply(setNum: Int): IndexedSeq[SetAssociation] = Array.range(0, setNum).flatMap(
    first => Array.range(first, setNum).map(second => new SetAssociation(first, second) )
  )

}