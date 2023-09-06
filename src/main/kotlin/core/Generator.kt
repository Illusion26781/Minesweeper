package core

class Generator(private val board: Board, private val maxBombs: Int) {
    fun generateBombs(x: Int, y: Int): ArrayList<Cell> {
        val bombs = ArrayList<Cell>()
        for (i in 0 until maxBombs) {
            val bomb = generateUnusedCoords(x, y)
            bombs.add(bomb)
            board.grid[bomb.x][bomb.y] = bomb

        }
        return bombs
    }

    fun generateCells() {
        for (x in 0 until board.xMax) {
            for (y in 0 until board.yMax) {
                if (board.grid[x][y].value == -1) continue
                board.grid[x][y].value = searchAroundCell(x, y)
            }
        }
    }

    private fun searchAroundCell(x: Int, y: Int): Int {
        var result = 0
        val points = generateAround(x, y)

        points.forEach {
            if (board.grid[it.x][it.y].value == -1) result++
        }

        return result
    }

    //this function is used to check the number of bombs for grid, and the multi hit method
    fun generateAround(x: Int, y: Int): ArrayList<Point> {
        val points = ArrayList<Point>(8)

        for (xRel in -1..1)
            for (yRel in -1..1) {
                if (xRel == 0 && yRel == 0 ||
                    !board.insideBoard(x + xRel, y+yRel)
                    ) continue

                points.add(Point(x + xRel, y + yRel))
        }

        return points
    }

    private fun generateUnusedCoords(x: Int, y: Int): Cell {
        var unique = false
        var x2: Int
        var y2: Int

        do {
            x2 = (0 until board.xMax).random()
            y2 = (0 until board.yMax).random()
            if (x == x2 && y == y2) continue

            if (board.grid[x2][y2].value == 0 && !around(x, y, x2, y2)) {
                unique = true
            }

        } while (!unique)

        return Cell(x2, y2, -1)
    }

    private fun around(x: Int, y: Int, x2: Int, y2: Int): Boolean {
        val xDelta = x - x2
        val yDelta = y - y2
        return (xDelta * xDelta <= 1 && yDelta * yDelta <= 1)
    }

    fun generateGrid() {
        for (x in 0 until board.xMax) {
            board.grid.add(ArrayList())
            for (y in 0 until board.yMax) board.grid[x].add(Cell(x, y, 0))
        }
    }
}