package com.list3.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.list3.todo.data.EventDatabase
import com.list3.todo.data.Event
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class NewEventViewModel(application: Application) : AndroidViewModel(application)
{
    private val mDb: EventDatabase? = EventDatabase.getInstance(application)
    private val allMovies = MutableLiveData<List<Event>>()

    fun storeEvent(title: String)
    {
        val event = Event()
        event.title = title

        GlobalScope.launch {
            mDb?.eventDao()?.insert(event)
        }
    }

    fun retrieveMovies(): LiveData<List<Event>>
    {
        GlobalScope.launch {
            val list = mDb?.eventDao()?.getAll()
            Timber.i("retrieveMovies list count ${list?.size}")
            allMovies.postValue(list)
        }
        return allMovies
    }

}