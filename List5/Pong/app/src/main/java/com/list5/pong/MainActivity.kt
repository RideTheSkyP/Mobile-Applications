package com.list5.pong

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.list5.pong.database.Game
import com.list5.pong.database.GameDatabase
import java.util.*
import kotlin.concurrent.schedule


const val DATABASE_NAME = "games"

enum class Player
{
    Left,
    Right
}

class MainActivity : AppCompatActivity()
{
    private lateinit var pongGame: PongGame
    private var cWidth: Int = 0
    private var cHeight: Int = 0
    private lateinit var scoreView: TextView
    private lateinit var ball: Shape
    private lateinit var paddleLeft: Shape
    private lateinit var paddleRight: Shape
    private var scoreLeft = 0
    private var scoreRight = 0
    private val paddleSize: Int = 250
    private val ballSize: Int = 24
    private var ballSpeed = Vector2D(10f, 0f)
    private lateinit var database: GameDatabase
    private var isActivityCreating = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isActivityCreating = true
        scoreView = this.findViewById(R.id.score)
        pongGame = this.findViewById(R.id.canvas)
        pongGame.onSurfaceCreated = this::setupGame
    }

    override fun onResume()
    {
        super.onResume()
        if (!isActivityCreating)
        {
            pongGame.surfaceResume {
                pauseGame()
            }
        }
    }

    override fun onPause()
    {
        super.onPause()
        isActivityCreating = false
        if(interval != null) interval!!.cancel()
        if(timeout != null) timeout!!.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        Thread {
            database.gameState().saveState(Game(scoreLeft, scoreRight))
        }.start()
    }

    private fun resumeGame(state: Game)
    {
        runOnUiThread {
            val builder = this.let {
                AlertDialog.Builder(it)
            }
            builder
                .setMessage(R.string.resumeMessage)
                .setTitle(R.string.app_name)
                .setPositiveButton(R.string.resumeYes) { _, _ ->
                    resetGame(true)
                    scoreLeft = state.scoreLeft
                    scoreRight = state.scoreRight
                    updateScoreboard()
                    startGame()
                }
                .setNegativeButton(R.string.resumeNo) { _, _ ->
                    startGame()
                }
                .setCancelable(false)
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun setupGame()
    {
        cWidth = pongGame.width
        cHeight = pongGame.height

        if (pongGame.shapes.size == 0)
        {
            ball = Shape(
                Vector2D(0f, 0f),
                Vector2D.from(ballSpeed),
                ShapeType.Ball,
                ballSize,
                Color.RED
            )

            paddleLeft = Shape(
                Vector2D(0f, 0f),
                Vector2D(0f, 0f),
                ShapeType.Paddle,
                paddleSize,
                Color.GREEN
            )

            paddleRight = Shape(
                Vector2D(cWidth.toFloat(), 0f),
                Vector2D(0f, 0f),
                ShapeType.Paddle,
                paddleSize,
                Color.GREEN
            )

            this.resetGame()
            pongGame.addShape(ball)
            pongGame.addShape(paddleLeft)
            pongGame.addShape(paddleRight)
        }

        Thread {
            database = Room.databaseBuilder(
                applicationContext,
                GameDatabase::class.java, DATABASE_NAME
            ).build()

            if(isActivityCreating)
            {
                val state = database.gameState().getState()
                if (state != null)
                {
                    resumeGame(state)
                }
                else
                {
                    startGame()
                }
            }
        }.start()
    }

    private fun startGame()
    {
        pauseGame()
        pongGame.onUpdate = {
            var potentialPaddleHit: Shape? = null
            if (ball.origin.x <= 0 + pongGame.paddleThickness)
            {
                potentialPaddleHit = paddleLeft
            } else if (ball.origin.x >= cWidth - pongGame.paddleThickness)
            {
                potentialPaddleHit = paddleRight
            }
            if (potentialPaddleHit != null)
            {
                val hitPosition = ball.origin.y - potentialPaddleHit.origin.y
                if (hitPosition in 0.0..paddleSize.toDouble())
                {
                    ball.speed.invertX()
                    val kick = hitPosition - paddleSize / 2
                    ball.speed.y = kick * 0.1f
                }
                else
                {
                    when (potentialPaddleHit)
                    {
                        paddleLeft -> registerScore(Player.Right)
                        paddleRight -> registerScore(Player.Left)
                    }
                }
            }

            if (ball.origin.y <= 0 || ball.origin.y >= cHeight)
            {
                ball.speed.invertY()
            }
            ball.move()
        }

        pongGame.onTouch = {
            for (touch in it)
            {
                if (touch.x < cWidth / 2)
                {
                    paddleLeft.origin.y = touch.y - paddleSize / 2
                }
                else
                {
                    paddleRight.origin.y = touch.y - paddleSize / 2
                }
            }
        }
    }

    private fun registerScore(winner: Player)
    {
        when (winner)
        {
            Player.Left -> scoreLeft++
            Player.Right -> scoreRight++
        }
        resetGame()
        updateScoreboard()
    }

    private fun resetGame(fullWipeDown: Boolean = false)
    {
        ball.origin.x = cWidth.toFloat() / 2 - ballSize / 2
        ball.origin.y = cHeight.toFloat() / 2 - ballSize / 2
        ball.speed.y = 0f
        ball.speed.invertX()

        if (fullWipeDown)
        {
            scoreLeft = 0
            scoreRight = 0
        }
    }

    private fun updateScoreboard()
    {
        runOnUiThread {
            scoreView.text = getString(R.string.scores, scoreLeft, scoreRight)
        }
    }

    private var interval: Timer? = null
    private var timeout: Timer? = null
    private fun pauseGame()
    {
        val time = 2
        if(interval != null) interval!!.cancel()
        if(timeout != null) timeout!!.cancel()
        if(ball.speed.x != 0f)
        {
            ballSpeed = Vector2D.from(ball.speed)
        }
        ball.speed = Vector2D(0f, 0f)
        var ticks = time
        interval = Timer("i", false)
        interval!!.schedule(0, 1000) {
            runOnUiThread {
                scoreView.text = getString(R.string.resume, ticks.toString())
                ticks--
                if (ticks == 0)
                {
                    this.cancel()
                }
            }
        }
        timeout = Timer("t", false)
        timeout!!.schedule(time.toLong() * 1000) {
            ball.speed = ballSpeed
            updateScoreboard()
        }
    }
}
