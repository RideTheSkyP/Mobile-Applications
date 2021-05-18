package com.example.arkanoid

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDatabaseDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg events: EventDBCreator)

    @get:Query("SELECT * FROM Events")
    val getAll: LiveData<List<EventDBCreator>>

    @Query("DELETE FROM Events")
    suspend fun deleteAll()
}