package gui

import core.Board

class MineSweeperConsole(private val board:Board)
{

    fun printBoard()
    {
        print(" ")
        for(i in 0 until board.xMax)
            print("  $i")
        println()
        repeat(board.xMax+2)
        {
            print("-  ")
        }
        println()

        for(y in 0 until board.yMax)
        {
            print("|")
            for(x in 0 until board.xMax)
            {
                if(board.grid[x][y].value!= -1)
                    print(" ")
                print(" ${board.grid[x][y].value}")
            }
            print("  |")
            print(" $y")
            println()
        }

        repeat(board.xMax+2)
        {
            print("-  ")
        }
        println()
    }

    fun printFound()
    {
        print(" ")
        for(i in 0 until board.xMax)
            print("  $i")
        println()
        repeat(board.xMax+2)
        {
            print("-  ")
        }
        println()

        for(y in 0 until board.yMax)
        {
            print("|")
            for(x in 0 until board.xMax)
            {
                if(board.grid[x][y].found)
                {
                    if(board.grid[x][y].value != -1)
                        print(" ")
                    print(" ${board.grid[x][y].value}")
                }
                else
                    print("  X")
            }
            print("  |")
            print(" $y")
            println()
        }

        repeat(board.xMax+2)
        {
            print("-  ")
        }
        println()
    }
}