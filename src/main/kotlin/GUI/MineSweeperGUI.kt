package GUI

import Core.Minesweeper
import java.awt.Color
import java.awt.Dimension
import java.awt.Insets
import java.awt.Label
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.Timer
import java.util.TimerTask
import javax.swing.JButton
import javax.swing.JFrame
import kotlin.concurrent.timerTask
import kotlin.time.Duration.Companion.seconds

class MineSweeperGUI(var mine: Minesweeper)
{
    private val frame = JFrame("Hello, Kotlin/Swing")
    private val buttons = ArrayList<JButton>()
    private var time = 0.seconds
    private val timeL = Label(time.toString())
    private val flagsL = Label(mine.nFlags.toString())
    private var timer = Timer()
    private var started = false
    init
    {
        generateCells()
        val xBound = 20 + 30*mine.board.xMax
        val yBound = 20 + 30*mine.board.yMax

        //frame.contentPane.add(scrollPane, BorderLayout.CENTER)
        frame.layout = null
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(xBound+120, yBound+50)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

        val newButton = JButton("New game")
        newButton.addActionListener {
            mine = Minesweeper(mine.board.xMax,mine.board.yMax,mine.maxBombs)
            updateBoard()
            timer.cancel()
            time = 0.seconds
            timer = Timer()
            timeL.text = time.toString()
            started = false
            flagsL.text = mine.nFlags.toString()
        }
        newButton.margin = Insets(0, 0, 0, 0)
        newButton.setBounds(xBound, 30, 70, 30)
        frame.add(newButton)
        
        timeL.setBounds(xBound, 70, 150, 30)
        flagsL.setBounds(xBound, 120, 150, 30)
        
        frame.add(timeL)
        frame.add(flagsL)
        
        
    }

    private fun generateCells()
    {
        var b : JButton
        val xOffset = 10
        val yOffset = 10
        for(x:Int in 0 until mine.board.xMax)
            for(y:Int in 0 until mine.board.yMax)
            {
                b = JButton() //creating instance of JButton
                b.setBounds(xOffset+30*x, yOffset+30*y, 30, 30) //x axis, y axis, width, height
                b.margin = Insets(0, 0, 0, 0)
                b.addActionListener()
                {
                    if(!started )
                        timer.scheduleAtFixedRate(timerTask {
                            time+=1.seconds
                            //println(time)
                            timeL.text = time.toString()
                            started=true
                        },0, 1000)
                    
                    if(!mine.board.grid[x][y].found)
                        mine.hit(x,y)
                    else
                        mine.hitAround(x,y)
                    
                    updateBoard()
                }
                b.addMouseListener(ButtonListener(b,x,y))
                buttons.add(b)
                frame.add(b) //adding button in JFrame*/
            }

    }

    inner class ButtonListener(private val button: JButton, private val x:Int, private val y:Int): MouseListener
    {
        override fun mouseClicked(e: MouseEvent?)
        {
        
        }

        override fun mousePressed(e: MouseEvent?)
        {
        }

        override fun mouseReleased(e: MouseEvent?)
        {
            if (e?.button == MouseEvent.BUTTON3) {
                mine.flag(x,y)
                updateBoard()
                flagsL.text = mine.nFlags.toString()
    
            }
        }

        override fun mouseEntered(e: MouseEvent?)
        {
        }

        override fun mouseExited(e: MouseEvent?)
        {
        }

    }

    private fun updateBoard()
    {
        var nButton = 0
        if(mine.over)
        {
            timer.cancel()
            timeL.text = time.toString() + " " + if(mine.won) "Game Won!" else "Game Lost!"
        }
        repeat(mine.board.xMax)
        {
                x ->
            repeat(mine.board.yMax)
            {
                y ->
                val cell = mine.board.grid[x][y]

                if(cell.flag)
                {
                    buttons[nButton].text = "F"
                    buttons[nButton].foreground = Color.RED
                }


                else if(cell.found)
                {
                    if(cell.value != -1)
                    {
                        buttons[nButton].text = cell.value.toString()
                        buttons[nButton].background = Color.LIGHT_GRAY
                        if(cell.value==0)
                            buttons[nButton].background = Color.GRAY

                    }
                    else
                    {
                        buttons[nButton].text = "X"
                        buttons[nButton].background = Color.RED

                    }
                }

                else
                {
                    buttons[nButton].text = ""
                    buttons[nButton].background = null
                    buttons[nButton].foreground = Color.BLACK
                }
                nButton++

            }
        }

    }
}