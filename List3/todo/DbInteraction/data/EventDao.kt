package com.list3.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao
{
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Insert
    fun insert(vararg items: Event)

//    @Query("DELETE FROM event")
//    suspend fun deleteAll()
}