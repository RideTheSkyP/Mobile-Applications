package com.example.arkanoid
import android.util.Log

class Brick (val width: Int,
             val height: Int,
             val xPos: Int,
             val yPos: Int) {

    var alive = true
    fun kill() {
        alive = false
    }

    fun getPosition(path: ArrayList<Pair<Int, Int>>) :
            Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var result = onLeft(path)
        if (result.first) {
            return Pair(Pair(result.second, result.third), Pair(-1, 1))
        } else {
            result = onRight(path)
            if (result.first) {
                return Pair(Pair(result.second, result.third), Pair(-1, 1))
            } else {
                result = onTop(path)
                if (result.first) {
                    return Pair(Pair(result.second, result.third), Pair(1, -1))
                } else {
                    result = onBottom(path)
                    if (result.first) {
                        return Pair(Pair(result.second, result.third), Pair(1, -1))
                    } else {
                        Log.d("infod", "error :(")
                    }
                }
            }
        }
        return Pair(Pair(0, 0), Pair(1, 1))
    }

    fun contains(x: Int, y:Int, r:Int) : Boolean {
        if (x >= xPos && x <= xPos + width &&
            y >= yPos && y <= yPos + height)
            return true
        return false
    }

    fun onLeft(path: ArrayList<Pair<Int, Int>>) : Triple<Boolean, Int, Int> {
        var currY = yPos
        for (p in path) {
            while (currY <= yPos + height) {
                if (p.first == xPos && p.second == currY)
                    return Triple(true, p.first, p.second)
                currY++
            }
            currY = yPos
        }
        return Triple(false, 0, 0)
    }

    fun onRight(path: ArrayList<Pair<Int, Int>>) : Triple<Boolean, Int, Int> {
        var currY = yPos
        for (p in path) {
            while (currY <= yPos + height) {
                if (p.first == xPos + width && p.second == currY)
                    return Triple(true, p.first, p.second)
                currY++
            }
            currY = yPos
        }
        return Triple(false, 0, 0)
    }

    fun onTop(path: ArrayList<Pair<Int, Int>>) : Triple<Boolean, Int, Int> {
        var currX = xPos
        for (p in path) {
            while (currX <= xPos + width) {
                if (p.first == currX && p.second == yPos)
                    return Triple(true, p.first, p.second)
                currX++
            }
            currX = xPos
        }
        return Triple(false, 0, 0)
    }

    fun onBottom(path: ArrayList<Pair<Int, Int>>) : Triple<Boolean, Int, Int> {
        Log.d("infod", "Bottom na y == ${yPos + height}")
        for (p in path) {
            Log.d("infod", "${p.first}, ${p.second}")
        }
        var currX = xPos
        for (p in path) {
            while (currX <= xPos + width) {
                if (p.first == currX && p.second == yPos + height)
                    return Triple(true, p.first, p.second)
                currX++
            }
            currX = xPos
        }
        return Triple(false, 0, 0)
    }
}