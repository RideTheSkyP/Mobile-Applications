package com.labs.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.labs.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    var bot : Boolean = true
    var field : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun menu(view: View)
    {
        when(view.id)
        {
            binding.player.id -> {bot=false; menuChangeColor(view, true, true);}
            binding.computer.id -> {bot=true; menuChangeColor(view, true, false);}
            binding.field3.id -> {field=true; menuChangeColor(view, false, true);}
            binding.field5.id -> {field=false; menuChangeColor(view, false, false);}
            binding.start.id -> {menuChangeState(view, false); }
        }
    }

    fun menuChangeState(view: View, visibility : Boolean)
    {
        var type = View.INVISIBLE
        if(visibility)
        {
            type = View.INVISIBLE
        }
        binding.player.visibility = type
        binding.computer.visibility = type
        binding.choseop.visibility = type
        binding.field5.visibility = type
        binding.field3.visibility = type
    }

    private fun menuChangeColor(view: View, buttonType : Boolean, button : Boolean)
    {
        val color : Int
        val opColor : Int
        if(button)
        {
            color = Color.parseColor("#6200EE")
            opColor = Color.DKGRAY
        }
        else
        {
            color = Color.DKGRAY
            opColor = Color.parseColor("#6200EE")
        }

        if(buttonType)
        {
            binding.computer.setBackgroundColor(opColor)
            binding.player.setBackgroundColor(color)
        }
        else
        {
            binding.computer.setBackgroundColor(color)
            binding.player.setBackgroundColor(opColor)
        }
    }


    fun createBoard(view: View, field : Boolean): Array<IntArray>
    {
        val size = if (field) 3 else 5
        return Array(size){IntArray(size)}
    }

    fun startGame(view: View, player : Boolean, field: Boolean)
    {
        var board = createBoard(view, field)
    }

    fun playerClicked(view: View)
    {

    }
}
