package monarezio.minesweeper.domain

import scala.annotation.tailrec
import scala.util.Random

import scala.math.Ordering.Implicits._

/**
  * Created by samuelkodytek on 05/12/2016.
  */
class Minesweeper(val rows: Int, val columns: Int, val mines: Int) {
  val handler = new Handler()

  var fields = generate()
  var vissibleFields = (0 to rows).map(i => (0 to columns).map(i => false).toList).toList

  /**
    *
    * @return returns a set of random positions for the bombs
    */
  def generate(): Set[Tuple2[Int, Int]] = {
    Stream.range(0, mines).map(i => handler.getUniqueTuple()).toSet
  }

  /**
    *
    * @param x
    * @param y
    * @return Amount of bombs around the position return 9 if the position is the bomb
    */
  def getValue(x: Int, y: Int): Int = {
    if(fields.contains(x, y)) return 9 //Bomb
    else fields.filter(i =>
      (x + 1, y) == i ||
      (x, y + 1) == i ||
      (x + 1, y + 1) == i ||
      (x - 1, y) == i ||
      (x, y - 1) == i ||
      (x - 1, y - 1) == i ||
      (x + 1, y - 1) == i ||
      (x - 1, y + 1) == i
    ).size //#ToDumbToUseBiggerThenOrSmallerThen :'(
  }

  def reveal(x: Int, y: Int): Unit = {
    vissibleFields = vissibleFields.updated(x, vissibleFields(x).updated(y, true))
  }


  /**
    * Created by samuelkodytek on 05/12/2016.
    * A Mutable class to serve generating random bomb poistions
    */
  class Handler {
    var saved: List[(Int, Int)] = List()

    @tailrec final def getUniqueTuple(): (Int, Int) = {
      val t = (Random.nextInt(rows), Random.nextInt(columns))
      if(saved.contains(t)) getUniqueTuple()
      else {
        saved = t :: saved
        t
      }
    }
  }
}