package Core

import java.util.*

class Minesweeper (xMax: Int, yMax: Int, val maxBombs: Int)
{
    //each cell has a number from -1 to 9, -1 meaning bomb, 9 meaning flag
    var nFlags = maxBombs
    private set
    private val bombs = ArrayList<Cell>(maxBombs)
    var over = false
    var board = Board(xMax, yMax)
    private var foundCells = xMax * yMax - maxBombs
    private var generator = Generator(board,maxBombs)
    var won = false
    private set
    init
    {
        generator.generateGrid()
    }

    fun hit(x: Int, y: Int)
    {
        if(!board.insideBoard(x,y) || over) return

        if(bombs.isEmpty())
        {
            generateBombs(x, y)
            generator.generateCells()
        }
        val cell = board.grid[x][y]
        if(cell.flag)
            return

        if(cell.value == -1)
        {
            end()
            return
        }

        cell.found = true
        foundCells--
        if(cell.value == 0)
            hitAround(x,y)

        if(foundCells == 0)
        {
            end()
            won = true
        }
    }

    fun flag(x: Int, y: Int)
    {
        if(!board.insideBoard(x,y)) return

        if(board.grid[x][y].flag)
        {
            nFlags++
            board.grid[x][y].flag = false
            return
        }

        if(nFlags > 0)
        {
            nFlags--
            board.grid[x][y].flag = true
        }

        if(nFlags == 0 && testFlags())
        {
            end()
            won = true
        }
    }

    fun hitAround(x: Int, y: Int)
    {
        val cells = generator.generateAround(x,y).filter{ board.insideBoard(it.x,it.y) }
        if(cells.size == board.grid[x][y].value)
            cells.forEach{
                if(!board.grid[it.x][it.y].flag)
                    flag(it.x,it.y)
            }
        else if(cells.count { board.grid[it.x][it.y].flag } == board.grid[x][y].value)
            cells.forEach {
                if(over)
                    return

                hit(it.x,it.y)
            }
    }

    private fun generateBombs(x:Int,y: Int)
    {
        bombs.addAll(generator.generateBombs(x,y))
    }

    private fun testFlags(): Boolean
    {
        bombs.forEach {
            if(!board.grid[it.x][it.y].flag)
                return false
        }
        return true
    }

    private fun end()
    {
        bombs.forEach {
            board.grid[it.x][it.y].found=true
        }
        over = true
    }
}