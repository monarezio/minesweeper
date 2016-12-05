package monarezio

import monarezio.minesweeper.MinesweeperUI
import monarezio.minesweeper.domain.Minesweeper

/**
  * Created by samuelkodytek on 05/12/2016.
  */
object main {
  def main(args: Array[String]): Unit = {
    val m = new MinesweeperUI(10, 10, 10)
    m.renderShown()
    m.play()
  }
}
