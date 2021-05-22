package com.list5.pong.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao
{
    @Query("SELECT * FROM game LIMIT 1")
    fun getState(): Game?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveState(state: Game)

    @Query("DELETE FROM game")
    fun deleteAll()
}