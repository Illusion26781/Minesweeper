package Core

import GUI.MineSweeperConsole
import GUI.MineSweeperGUI


fun main()
{
    //MineSweeperGUI()

    val minesweeper = Minesweeper(10,10,10)
    MineSweeperConsole(minesweeper.board)
    MineSweeperGUI(minesweeper)





}