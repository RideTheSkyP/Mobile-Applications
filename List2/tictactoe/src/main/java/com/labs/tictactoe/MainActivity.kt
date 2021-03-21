package com.labs.tictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.iterator
import androidx.core.view.size
import com.labs.tictactoe.databinding.ActivityMainBinding
import com.labs.tictactoe.databinding.Board3Binding
import com.labs.tictactoe.databinding.Board5Binding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingBoard3: Board3Binding
    private lateinit var bindingBoard5: Board5Binding
    var bot : Boolean = true
    var field : Boolean = true
    lateinit var board : Array<Array<Int>>
    var currentTurn : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingBoard3 = Board3Binding.inflate(layoutInflater)
        bindingBoard5 = Board5Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun menu(view: View)
    {
        when(view.id)
        {
            binding.player.id -> {bot=false; menuChangeButtonColor(view, true, true);}
            binding.computer.id -> {bot=true; menuChangeButtonColor(view, true, false);}
            binding.field3.id -> {field=true; menuChangeButtonColor(view, false, true);}
            binding.field5.id -> {field=false; menuChangeButtonColor(view, false, false);}
            binding.start.id -> {
                board = if (field) Array(3) {Array(3) {0}} else Array(5) {Array(5) {0}};
                menuChangeState(true);}

        }
    }

    fun menuChangeState(state : Boolean)
    {
        val board = if (field) bindingBoard3.root else bindingBoard5.root
        val setView : View = if (state) board else binding.root
        setContentView(setView)
    }

    fun returnToMenu(view: View)
    {
        recreate()
        setContentView(binding.root)
    }

    private fun menuChangeButtonColor(view: View, buttonType : Boolean, button : Boolean)
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

    @SuppressLint("UseCompatLoadingForDrawables")
    fun turnClicked(view: View)
    {
        val imgView: ImageView = view as ImageView
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
        val sign = if (currentTurn) "o" else "x"
        Toast.makeText(this, "$sign wins!", Toast.LENGTH_SHORT).show()
        recreate()
        menuChangeState(false)
    }
}
