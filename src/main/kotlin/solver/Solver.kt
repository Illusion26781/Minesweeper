package solver

import core.Cell
import core.Minesweeper
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.concurrent.fixedRateTimer

class Solver (var ms: Minesweeper) {

    val cells = ArrayList<Cell>()
    init {
        val x = (0 until ms.board.xMax).random()
        val y = (0 until ms.board.yMax).random()
        ms.hit(x, y)

    }
    private fun fillCells() {
        ms.board.grid.forEach { x ->
            x.forEach {
                if (it.found && it.value > 0)
                    cells.add(it)
            }
        }
    }

    fun solve(updateBoard: ()-> Unit) {
        updateBoard()
        fixedRateTimer(period = 1000, action = {
            fillCells()
            cells.forEach { cell ->
                ms.hitAround(cell.x, cell.y)
            }
            TimeUnit.SECONDS.sleep(1)
            updateBoard()
            if(ms.over)
                cancel()
        })

    }
}