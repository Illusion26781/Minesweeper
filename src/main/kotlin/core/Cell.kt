package core


data class Point(val x: Int, val y: Int)

data class Cell(val x: Int, val y: Int, var value :Int, var found :Boolean = false, var flag :Boolean = false)

