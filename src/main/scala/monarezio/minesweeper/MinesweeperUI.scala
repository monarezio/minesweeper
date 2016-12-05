package monarezio.minesweeper

import monarezio.minesweeper.domain.Minesweeper

import scala.io.StdIn

/**
  * Created by samuelkodytek on 05/12/2016.
  */
class MinesweeperUI(val rows: Int, val columns: Int, val mines: Int) {
  private val domain: Minesweeper = new Minesweeper(rows, columns, mines)

  def renderShown(): Unit = {
    (0 to rows).foreach(i => {
      (0 to columns).foreach(j => print(domain.getValue(i, j) + "|"))
      println()
    })
    println()
  }

  def renderHidden(): Unit = {
    (0 to rows).foreach(i => {
      (0 to columns).foreach(j => {
        if(!domain.vissibleFields(i)(j)) print("X|")
        else print(domain.getValue(i, j) + "|")
      })
      println()
    })
  }

  def listen(): (Int, Int) = {
    val line = StdIn.readLine()
    val coordinates = line.split(" ").map(i => i.toInt)
    return (coordinates.head, coordinates.last)
  }

  def play(): Unit = {
    renderHidden()

    val coordinates = listen()
    domain.reveal(coordinates._1, coordinates._2)
    play()
  }
}
