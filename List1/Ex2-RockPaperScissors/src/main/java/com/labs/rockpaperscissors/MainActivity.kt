package com.labs.rockpaperscissors

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.labs.rockpaperscissors.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding;
    private var won = 0;
    private var gamesPlayed = 0;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    @SuppressLint("SetTextI18n")
    private fun buttonTriggered(view: View, button:String)
    {
        gamesPlayed++;
        val computerChoice = computerRoll(view)
        binding.computerChoice.text = computerChoice

        if (button == computerChoice)
            won += 0;
        else if (button == "rock" && computerChoice == "scissors")
            won++;
        else if (button == "scissors" && computerChoice == "paper")
            won++;
        else if (button == "paper" && computerChoice == "rock")
            won++;
        else
            won += 0;
        val percentage:Float = won.toFloat() / gamesPlayed.toFloat() * 100;
        binding.historyPercentage.text = "You won $won out of $gamesPlayed games.\n Won percentage is ${"%.2f".format(percentage)}%"
    }

    private fun computerRoll(view: View): String
    {
        when (Random.nextInt(3))
        {
           0->{binding.imageView.setImageResource(R.drawable.rock); return "rock";}
           1->{binding.imageView.setImageResource(R.drawable.paper); return "paper";}
           2->{binding.imageView.setImageResource(R.drawable.scissors); return "scissors";}
        }
        return "rock";
    }

    fun rockClicked(view: View)
    {
        buttonTriggered(view, "rock");
        binding.imageView2.setImageResource(R.drawable.rock)
    }

    fun paperClicked(view: View)
    {
        buttonTriggered(view, "paper");
        binding.imageView2.setImageResource(R.drawable.paper)
    }

    fun scissorsClicked(view: View)
    {
        buttonTriggered(view, "scissors");
        binding.imageView2.setImageResource(R.drawable.scissors)
    }
}