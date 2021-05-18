package com.example.arkanoid

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Math.abs
import java.lang.Math.random
import kotlin.math.roundToInt

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback
{
    var thread : GameThread
    var ballX = -1
    var ballY = -1
    var dx = 15
    var dy = 15
    var SIZE = 25
    var rectHSize = 200
    var rectX = 0
    var rectY = 1584
    var bricks: ArrayList<Brick> = ArrayList()
    var spacing = 5
    var oldBallX = 0
    var oldBallY = 0
    var points = 0
    var lives = 3
    var over = false
    var prefName = "arkanoidPreferences"
    var win = 0

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }


    override fun surfaceDestroyed(holder: SurfaceHolder)
    {
        thread.running = false
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder)
    {
        val d = loadData()
        if (d == null) {
            reset()
        } else {
            bricks = d
            resetBall()
        }
        thread.running = true
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int)
    {
    }

    fun pause()
    {
        thread.running = false
        thread.join()
    }

    fun resume()
    {
        if (!thread.running)
        {
            thread.join()
            thread.running = false
            val d = loadData()
            if (d == null)
            {
                reset()
            }
            else
            {
                bricks = d
                resetBall()
            }
        }
    }

    fun saveData()
    {
        val s = context.getSharedPreferences(prefName, MODE_PRIVATE)
        val prefEditor = s.edit()
        val gson = Gson()
        val json = gson.toJson(bricks)
        prefEditor.putString("Object", json)
        prefEditor.commit()
    }

    fun loadData() : ArrayList<Brick>?
    {
        val gson = Gson()
        val json =
            context.getSharedPreferences(prefName, MODE_PRIVATE)
                .getString("Object", "")
        if (json == "")
            return null
        else {
            val itemType = object : TypeToken<ArrayList<Brick>>() {}.type
            return gson.fromJson<ArrayList<Brick>>(json, itemType)
        }
    }

    fun incrementLife()
    {
        lives++
        if (lives == 3)
        {
            over = false
            reset()
        }
    }

    fun resetBall()
    {
        ballY = width/2
        ballX = height/3
    }

    fun updateBall()
    {
        if (ballX == -1 && ballY == -1)
        {
            ballX = width / 2
            ballY = height / 2
        }
        else
        {
            oldBallX = ballX
            oldBallY = ballY
            ballX += dx
            ballY += dy
            if (ballY + SIZE + dy >= rectY)
            {
                if (ballX <= rectX + rectHSize && ballX + dx >= rectX)
                {
                    val mid = rectX + rectHSize/2
                    dx = if (ballX < mid)
                        -abs(dx)
                    else
                        abs(dx)
                    dy = -dy
                }
                else
                {
                    lives--
                    resetBall()
                }
            }
            else
            {
                if (ballX <= 0 || ballX + SIZE >= width)
                {
                    dx = -dx
                }
                if (ballY <= 0 || ballY + SIZE >= height)
                {
                    dy = -dy
                }
            }
        }
    }

    fun updateBricks()
    {
        var counter = 0
        for (b in bricks)
        {
            if (b.alive && b.contains(ballX, ballY, SIZE))
            {
                val i = dx/abs(dx)
                val j = dy/abs(dy)
                val path: ArrayList<Pair<Int, Int>> = ArrayList()
                var currX = oldBallX
                var currY = oldBallY
                while (currX != ballX && currY != ballY)
                {
                    path.add(Pair(currX, currY))
                    currX += i
                    currY += j
                }
                path.add(Pair(currX, currY))
                val newCoords = b.getPosition(path)
                ballX = newCoords.first.first
                ballY = newCoords.first.second
                dx *= newCoords.second.first
                dy *= newCoords.second.second
                b.kill()
            }
            else if (!b.alive)
            {
                counter++
            }
        }
        points = counter
        if (points == 16 && win == 0)
        {
            win = 3
        }
    }

    fun reset()
    {
        bricks.clear()
        lives = 3
        val d = width/4
        val h = height/16
        var currX = 0
        var currY = 0
        for (i in 1..4) {
            while (currX < width)
            {
                bricks.add(Brick(d - spacing,
                    h - spacing,
                    currX + spacing,
                    currY + spacing))
                currX += d
            }
            currX = 0
            currY += h
        }
        resetBall()
        over = false
        points = 0
    }

    fun update()
    {
        updateBricks()
        updateBall()
        if (lives == 0)
        {
            over = true
        }
        if (win == -1)
        {
            win = 0
            reset()
        }
        saveData()
    }

    override fun draw(canvas: Canvas?)
    {
        super.draw(canvas)
        if (canvas == null) return
        canvas.drawARGB(255, 252, 231, 125)
        if (win > 0)
        {
            val s = Paint()
            s.setARGB(255, 249, 129, 103)
            s.textSize = 200f
            s.textAlign = Paint.Align.CENTER
            canvas.drawText("WIN!", width/2f, height/3f, s)
            win--
            if (win == 0)
                win = -1
        }
        else if (over)
        {
            val s = Paint()
            s.setARGB(255, 249, 129, 103)
            s.textSize = 200f
            s.textAlign = Paint.Align.CENTER
            canvas.drawText("Game over", width / 2f, 2 * (height / 3f), s)
            val b = BitmapFactory.decodeResource(resources, R.drawable.hearts)
            var startAt = width / 2 - 120f
            for (i in 1..lives)
            {
                canvas.drawBitmap(b, startAt, 2 * (height / 3f) + 100f, null)
                startAt += 70
            }

        }
        else
        {
            val red = Paint()
            red.setARGB(255, 249, 129, 103)
            if (ballX == -1 && ballY == -1)
            {
                ballX = width / 2
                ballY = height / 2
            }
            canvas.drawOval(
                RectF(
                    ballX.toFloat(),
                    ballY.toFloat(),
                    ballX + SIZE.toFloat(),
                    ballY + SIZE.toFloat()
                ),
                red
            )
            canvas.drawRect(
                rectX.toFloat(),
                rectY - 20.toFloat(),
                rectX + rectHSize.toFloat(),
                rectY - 10.toFloat(), red
            )
            for (b in bricks)
            {
                if (b.alive)
                {
                    canvas.drawRect(
                        b.xPos.toFloat(),
                        b.yPos.toFloat(),
                        (b.xPos + b.width).toFloat(),
                        (b.yPos + b.height).toFloat(),
                        red
                    )
                }
            }
            val pStyle = Paint()
            pStyle.setARGB(100, 249, 129, 103)
            pStyle.textSize = 100f
            canvas.drawText("$points", width / 2f - 50, 2 * (height / 3f), pStyle)
            val b = BitmapFactory.decodeResource(resources, R.drawable.hearts)
            var startAt = width / 2 - 120f
            for (i in 1..lives) {
                canvas.drawBitmap(b, startAt, 2 * (height / 3f) + 100f, null)
                startAt += 70
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        rectX = event.x.roundToInt() - (rectHSize/2)
        return true
    }
}