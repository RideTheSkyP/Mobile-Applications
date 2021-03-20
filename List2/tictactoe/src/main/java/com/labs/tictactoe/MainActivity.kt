package com.labs.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
            binding.start.id -> {menuChangeState(true);}
        }
    }

    fun menuChangeState(state : Boolean)
    {
//        val setView : View = if (state) bindingBoard3.root else binding.root
        val setView : View = if (state) bindingBoard5.root else binding.root
        setContentView(setView)
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

    fun turnClicked(view: View)
    {
        val imgView: ImageView = view as ImageView
        val img = if (currentTurn) R.drawable.x else R.drawable.o
        imgView.setImageResource(img)
        imgView.isEnabled = false
        currentTurn = !currentTurn
    }

    fun checkWin(field : ImageView)
    {
        menuChangeState(false)
    }
}
