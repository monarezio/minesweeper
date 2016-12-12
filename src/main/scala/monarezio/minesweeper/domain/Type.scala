package monarezio.minesweeper.domain

/**
  * Created by samuelkodytek on 12/12/2016.
  */
trait Type

case object Vissible extends Type
case object Hidden extends Type
case object Flag extends Type
case object QuestionMark extends Type
