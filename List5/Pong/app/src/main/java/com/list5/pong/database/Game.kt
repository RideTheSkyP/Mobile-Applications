package com.list5.pong.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    val scoreLeft: Int,
    val scoreRight: Int,
    @PrimaryKey
    val uid: Int = 1,
)