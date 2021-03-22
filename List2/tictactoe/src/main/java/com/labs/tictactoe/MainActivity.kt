package com.labs.tictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.labs.tictactoe.databinding.ActivityMainBinding
import com.labs.tictactoe.databinding.Board3Binding
import com.labs.tictactoe.databinding.Board5Binding
import kotlin.random.Random

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingBoard3: Board3Binding
    private lateinit var bindingBoard5: Board5Binding
    lateinit var view : View
    var bot : Boolean = true
    var field : Boolean = true
    lateinit var board : Array<Array<Int>>
    var currentTurn : Boolean = true
    var won : String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingBoard3 = Board3Binding.inflate(layoutInflater)
        bindingBoard5 = Board5Binding.inflate(layoutInflater)
        setContentView(binding.root)
        if (won.isEmpty()) binding.win.text = "$won won last game!"
    }

    fun menu(view: View)
    {
        when(view.id)
        {
            binding.player.id -> {
                bot = false; menuChangeButtonColor(view, true, true);
            }
            binding.computer.id -> {
                bot = true; menuChangeButtonColor(view, true, false);
            }
            binding.field3.id -> {
                field = true; menuChangeButtonColor(view, false, true);
            }
            binding.field5.id -> {
                field = false; menuChangeButtonColor(view, false, false);
            }
            binding.start.id -> {
                board = if (field) Array(3) {Array(3) {0}} else Array(5) {Array(5) {0}}
                menuChangeState(true)
            }
        }
    }

    fun menuChangeState(state: Boolean)
    {
        val board = if (field) bindingBoard3.root else bindingBoard5.root
        view = if (state) board else binding.root
        setContentView(view)
    }

    fun returnToMenu(view: View)
    {
        recreate()
        setContentView(binding.root)
    }

    private fun menuChangeButtonColor(view: View, buttonType: Boolean, button: Boolean)
    {
        val color : Int = if (button) Color.parseColor("#6200EE") else Color.DKGRAY
        val opColor : Int = if (button) Color.DKGRAY else Color.parseColor("#6200EE")

        if(buttonType)
        {
            binding.computer.setBackgroundColor(opColor)
            binding.player.setBackgroundColor(color)
        }
        else
        {
            binding.field3.setBackgroundColor(color)
            binding.field5.setBackgroundColor(opColor)
        }
    }

    fun turnClicked(currView: View)
    {
        val imgView: ImageView = currView as ImageView
        val img = if (currentTurn) R.drawable.x else R.drawable.o
        val points : Int = if (currentTurn) 1 else -1
        val boardSize : Int = if (field) 3 else 5
        imgView.setImageResource(img)
        imgView.isEnabled = false
        currentTurn = !currentTurn
        val currentCell = resources.getResourceEntryName(imgView.id).takeLast(1).toInt()
        val column = currentCell % boardSize
        val row = currentCell / boardSize
        board[row][column] = points
        checkWin()
        if (won.isEmpty()) botTurn()
    }

    fun botTurn()
    {
        val img = if (currentTurn) R.drawable.x else R.drawable.o
        val boardSize : Int = if (field) 3 else 5
        val points : Int = if (currentTurn) 1 else -1
        var found = findEmptyImage(-1)
        var imgBot : ImageView = findViewById(found)
        if(imgBot.drawable != null)
        {
            found = findEmptyImage(checkBoard())
            imgBot = findViewById(found)
        }
        imgBot.setImageResource(img)
        imgBot.isEnabled = false
        currentTurn = !currentTurn
        val currentCell = resources.getResourceEntryName(imgBot.id).takeLast(1).toInt()
        val column = currentCell % boardSize
        val row = currentCell / boardSize
        board[row][column] = points
        checkWin()
    }

    fun checkBoard(): Int
    {
        var count = 0
        var cell = 0
        for (i in board.indices)
        {
            for (j in board.indices)
            {
                if (board[i][j] == 0)
                {
                    cell = i + j
                    count ++
                }
            }
        }

        if (count==0)
        {
            Toast.makeText(this, "Tie!", Toast.LENGTH_SHORT).show()
            recreate()
            menuChangeState(false)
            return 0
        }
        else
        {
            return cell
        }
    }

    fun findEmptyImage(findId : Int): Int
    {
        val x = Random.nextInt(if (field) 3 else 5)
        val y = Random.nextInt(if (field) 3 else 5)
        val currId = if (findId != -1) findId else x * y + x + y
        var found = R.id.f0
        when (currId)
        {
            0 -> found = R.id.f0
            1 -> found = R.id.f1
            2 -> found = R.id.f2
            3 -> found = R.id.f3
            4 -> found = R.id.f4
            5 -> found = R.id.f5
            6 -> found = R.id.f6
            7 -> found = R.id.f7
            8 -> found = R.id.f8
            9 -> found = R.id.f9
            10 -> found = R.id.f10
            11 -> found = R.id.f11
            12 -> found = R.id.f12
            13 -> found = R.id.f13
            14 -> found = R.id.f14
            15 -> found = R.id.f15
            16 -> found = R.id.f16
            17 -> found = R.id.f17
            18 -> found = R.id.f18
            19 -> found = R.id.f19
            20 -> found = R.id.f20
            21 -> found = R.id.f21
            22 -> found = R.id.f22
            23 -> found = R.id.f23
            24 -> found = R.id.f24
        }
        return found
    }

    fun checkWin()
    {
        var x: Int
        var y: Int
        var d1 = 0
        var d2 = 0

        for (i in board.indices)
        {
            x = 0
            y = 0

            for (j in board.indices)
            {
                if (board[i][j] != 0)
                {
                    if (j == i)
                    {
                        d1 += board[i][j]
                    }
                    if (j + i == board.size-1)
                    {
                        d2 += board[i][j]
                    }
                    x += board[i][j]
                }

                if (board[j][i] != 0)
                {
                    y += board[j][i]
                }

                if (x == board.size || x == -board.size || y == board.size || y == -board.size)
                {
                    endGame()
                }
                else if (d1 == board.size || d1 == -board.size || d2 == board.size || d2 == -board.size)
                {
                    endGame()
                }
            }
        }
    }

    fun endGame()
    {
        won = if (currentTurn) "O" else "X"
        Toast.makeText(this, "$won won!", Toast.LENGTH_SHORT).show()
        recreate()
        menuChangeState(false)
    }
}
