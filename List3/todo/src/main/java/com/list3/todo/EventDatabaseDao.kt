package com.list3.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDatabaseDao
{

//    @Query("SELECT * FROM director WHERE did = :id LIMIT 1")
//    suspend fun findDirectorById(id: Long): Director?

//    @Query("SELECT * FROM director WHERE full_name = :fullName LIMIT 1")
//    suspend fun findDirectorByName(fullName: String?): Director?

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(director: Director): Long
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(vararg directors: Director)
//
//    @Update(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun update(director: Director)
//
//    @Query("DELETE FROM director")
//    suspend fun deleteAll()
//
//    @get:Query("SELECT * FROM director ORDER BY full_name ASC")
//    val allDirectors: LiveData<List<Director>>

//    @Query("SELECT * FROM Events WHERE icon = :iconPos LIMIT 1")
//    suspend fun sortByIcon(iconPos: Int): EventDBCreator?
//
//    @Query("SELECT * FROM Events WHERE priority = :prior LIMIT 1")
//    suspend fun sortByDate(prior: Int): EventDBCreator?
//
//    @Query("SELECT * FROM Events WHERE icon = :iconPos LIMIT 1")
//    suspend fun sortByPriority(iconPos: Int): EventDBCreator?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg events: EventDBCreator)

    @get:Query("SELECT * FROM Events")
    val getAll: LiveData<List<EventDBCreator>>

    @Query("DELETE FROM Events")
    suspend fun deleteAll()

//    @Query("DELETE FROM Events")
//    suspend fun deleteAll()
}