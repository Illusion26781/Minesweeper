package core

import gui.MineSweeperGUI
import solver.Solver
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.swing.SwingUtilities


fun main() {

    var minesweeper = Minesweeper()
    val gui = MineSweeperGUI(minesweeper)
    var solver: Solver

    gui.newGame = {
        minesweeper = Minesweeper()
        gui.mine = minesweeper
        solver = Solver(minesweeper)
        solver.solve(gui::updateBoard)
    }
    solver = Solver(minesweeper)
    solver.solve(gui::updateBoard)

}