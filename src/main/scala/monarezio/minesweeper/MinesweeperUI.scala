package monarezio.minesweeper

import monarezio.minesweeper.domain.{Flag, Hidden, Minesweeper, QuestionMark}

import scala.io.StdIn
import scala.util.Try
import scala.util.control.Exception

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
        domain.vissibleFields(i)(j) match {
          case Hidden => print("X|")
          case QuestionMark => print("?|")
          case Flag => print("p|")
          case _ => print(domain.getValue(i, j) + "|")
        }
      })
      println()
    })
  }

  private def tryToInt( s: String ) = Try(s.toInt).toOption

  def listen(): (String, Int, Int) = {
    println("[command] [x] [y]")
    val line = StdIn.readLine()
    val command = line.split(" ").map(i => i)
    if(command.size == 3 && !tryToInt(command(1)).isEmpty && !tryToInt(command.last).isEmpty)
      return (command.head, command(1).toInt, command.last.toInt)
    listen()
  }

  def play(): Unit = {
    renderHidden()

    val command = listen()
    if(command._1.toLowerCase == "r") //Reveal
      domain.reveal(command._3, command._2)
    else if(command._1.toLowerCase == "f") //Flag
      domain.flag(command._3, command._2)
    else if(command._1.toLowerCase == "q") //Question mark
      domain.question(command._3, command._2)
    else {
      println("Not a valid command use: ")
      println("r - to reveal")
      println("f - to flag")
      println("q - to question mark")
    }
    if(domain.hasWon())
      println("Congratulations, you won!")
    else if(domain.isDead()) {
      renderShown()
      println("Game over, you have hit a bomb!")
    }
    else
      play()
  }
}
