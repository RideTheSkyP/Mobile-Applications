package com.list5.pong.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Game::class], version = 1)
abstract class GameDatabase : RoomDatabase()
{
    abstract fun gameState(): GameDao
}