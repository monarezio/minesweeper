package monarezio

import monarezio.minesweeper.domain.Minesweeper

/**
  * Created by samuelkodytek on 05/12/2016.
  */
object main {
  def main(args: Array[String]): Unit = {
    new Minesweeper(10, 10, 10)
  }
}
