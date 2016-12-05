package monarezio.minesweeper

import monarezio.minesweeper.domain.Minesweeper

/**
  * Created by samuelkodytek on 05/12/2016.
  */
class MinesweeperUI(val rows: Int, val columns: Int, val mines: Int) {
  val domain: Minesweeper = new Minesweeper(rows, columns, mines)
}
