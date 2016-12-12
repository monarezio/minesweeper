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
  var vissibleFields: List[List[Type]] = (0 to rows).map(i => (0 to columns).map(i => Hidden).toList).toList

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
    ).size //#TooDumbToUseBiggerThenOrSmallerThen :'(
  }

  /**
    * Spreads visibility like cancer :)
    * @param x
    * @param y
    */
  def reveal(x: Int, y: Int): Unit = {
    if(vissibleFields(x)(y) != Flag) {
      def innerReveal(x: Int, y: Int): Unit = {
        vissibleFields = vissibleFields.updated(x, vissibleFields(x).updated(y, Vissible))
        if(getValue(x, y) == 0) {
          if(isPointOnFieldAndIsHidden(x + 1, y)) innerReveal(x + 1, y)
          if(isPointOnFieldAndIsHidden(x + 1, y + 1)) innerReveal(x + 1, y + 1)
          if(isPointOnFieldAndIsHidden(x, y + 1)) innerReveal(x, y + 1)
          if(isPointOnFieldAndIsHidden(x - 1, y)) innerReveal(x - 1, y)
          if(isPointOnFieldAndIsHidden(x - 1, y - 1)) innerReveal(x - 1, y - 1)
          if(isPointOnFieldAndIsHidden(x, y - 1)) innerReveal(x, y - 1)
          if(isPointOnFieldAndIsHidden(x + 1, y - 1)) innerReveal(x + 1, y - 1)
          if(isPointOnFieldAndIsHidden(x - 1, y + 1)) innerReveal(x - 1, y + 1)
        }
      }
      innerReveal(x, y)
    }
  }

  def flag(x: Int, y: Int): Unit = {
    if(vissibleFields(x)(y) != Vissible)
      vissibleFields = vissibleFields.updated(x, vissibleFields(x).updated(y, Flag))
  }

  def question(x: Int, y: Int): Unit = {
    if(vissibleFields(x)(y) != Vissible)
      vissibleFields = vissibleFields.updated(x, vissibleFields(x).updated(y, QuestionMark))
  }

  def hasWon(): Boolean = {
    vissibleFields.map(i => i.filter(j => j == Hidden).size).sum == fields.size
  }

  def isDead(): Boolean = !fields.filter(i => vissibleFields(i._1)(i._2) == Vissible).isEmpty


  private def isPointOnFieldAndIsHidden(x: Int, y: Int): Boolean = {
    x >= 0 && x <= rows && y >= 0 && y <= columns && vissibleFields(x)(y) != Vissible
  }

  /**
    * Created by samuelkodytek on 05/12/2016.
    * A Mutable class to serve generating random bomb positions
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