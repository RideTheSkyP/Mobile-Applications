package com.list3.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MoviesViewModel(application: Application) : AndroidViewModel(application)
{

    private val movieDao: EventDatabaseDao = EventDatabase.getDatabase(application).eventDao()

    val moviesList: LiveData<List<EventDBCreator>> = movieDao.getAll

    fun insert(vararg events: EventDBCreator) {
        movieDao.insert(*events)
    }

//    suspend fun update(movie: EventDBCreator) {
//        movieDao.update(movie)
//    }

    suspend fun deleteAll() {
        movieDao.deleteAll()
    }
}