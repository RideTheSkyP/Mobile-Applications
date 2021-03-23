package com.lab2.hangman

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Button
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.lab2.hangman.databinding.ActivityMainBinding
import com.lab2.hangman.databinding.GameEndBinding
import java.util.*
import kotlin.random.Random


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: GameEndBinding
    private var lettersButtons = mutableMapOf<String, Button>()
    lateinit var word : String
    var errors = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val letters = R.array.letters
        lateinit var currentRow : TableRow

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        for ((counter, letter) in resources.getStringArray(letters).withIndex())
        {
            val divider = width / 180
            if (counter.rem(divider) == 0)
            {
                currentRow = TableRow(this)
                val params = TableRow.LayoutParams()
                params.width = TableRow.LayoutParams.MATCH_PARENT
                currentRow.layoutParams = params
                currentRow.gravity = Gravity.CENTER
                binding.keyboard.addView(currentRow)
            }

            currentRow.addView(Button(this).apply
            {
                text = letter
                val params = TableRow.LayoutParams()
                params.width = 180
                params.setMargins(1, 1, 1, 1)
                this.layoutParams = params
                this.setTextColor(Color.WHITE)
                setOnClickListener{keyboardButtonClicked(this, letter.toLowerCase(Locale.ROOT))}
                lettersButtons[letter] = this
            })
        }
        start()
    }

    private fun start()
    {
        errors = 0
        binding.imageView.setImageResource(R.drawable.hangman0)
        lettersButtons.forEach { _, v ->
            v.isClickable = true
            v.setBackgroundColor(Color.DKGRAY)
        }
        val words = R.array.words
        val size = resources.getStringArray(words).size
        word = resources.getStringArray(words)[Random.nextInt(size)]
        binding.wordToGuess.text = word.replace(regex = Regex("(.)"), replacement = "?")
    }

    private fun keyboardButtonClicked(button: Button, letter: String)
    {
        if (checkLetterInWord(letter))
            button.setBackgroundColor(Color.GREEN)
        else
            button.setBackgroundColor(Color.RED)
        button.isClickable = false
    }

    private fun checkLetterInWord(letter: String): Boolean
    {
        if (word.contains(letter))
        {
            for (i in word.indices)
            {
                if (word[i] == letter[0])
                {
                    binding.wordToGuess.text = binding.wordToGuess.text.replaceRange(i, i + 1, letter)
                }
            }
            if (!binding.wordToGuess.text.contains("?"))
            {
                endGame(true)
            }
            return true
        }
        else
        {
            errors++
            redrawHangman()
            return false
        }
    }

    private fun redrawHangman()
    {
        if (errors <= 7) binding.imageView.setImageResource(resources.getIdentifier("hangman$errors", "drawable", packageName))
        if (errors == 7) endGame(false)
    }

    private fun endGame(won: Boolean)
    {
        val dialog = Dialog(this)
        bindingDialog = GameEndBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(false)
        if(won)
        {
            bindingDialog.gameFinished.text = getString(R.string.won)
            bindingDialog.message.text = getString(R.string.secret_word_missed, word, errors)
        }
        else
        {
            bindingDialog.gameFinished.text = getString(R.string.game_finished)
            bindingDialog.message.text = getString(R.string.secret_word, word)
        }

        bindingDialog.againButton.setOnClickListener{dialog.dismiss(); start()}
        dialog.show()
    }
}
