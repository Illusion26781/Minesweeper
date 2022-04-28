package Core

import GUI.MineSweeperConsole
import GUI.MineSweeperGUI


fun main()
{
    //MineSweeperGUI()

    val minesweeper = Minesweeper(10,10,10)
    val console = MineSweeperConsole(minesweeper.board)
    val gui = MineSweeperGUI(minesweeper)
    /*console.printFound()
    while(!minesweeper.over)
    {
        val hits = readln().split(" ").map {it.toInt()}
        minesweeper.hit(hits[0],hits[1])
        console.printFound()
    }*/



}