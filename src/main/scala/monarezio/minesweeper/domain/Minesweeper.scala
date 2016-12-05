package monarezio.minesweeper.domain

import scala.annotation.tailrec
import scala.util.Random

/**
  * Created by samuelkodytek on 05/12/2016.
  */
class Minesweeper(val rows: Int, val columns: Int, val mines: Int) {
  val handler = new Handler()
  val fields = generate()
  println(fields)

  def generate(): Set[Tuple2[Int, Int]] = {
    Stream.range(0, mines).map(i => handler.getUniqueTuple()).toSet
  }

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