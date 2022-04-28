package Core

class Board(val xMax: Int, val yMax: Int)
{
    var grid = ArrayList<ArrayList<Cell>>()

    fun insideBoard(x:Int,y:Int):Boolean
    {
        if(x < 0 || xMax <= x ) return false
        if(y < 0 || yMax <= y ) return false
        if(grid[x][y].found) return false
        return true
    }


}