package com.list3.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.list3.todo.database.Event
import com.list3.todo.database.EventDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewEventViewModel(application: Application) : AndroidViewModel(application)
{
    private val mDb: EventDatabase? = EventDatabase.getInstance(application)
    private val allMovies = MutableLiveData<List<Event>>()

    fun storeEvent(title: String, description: String, priority: Int, icon: Int, date: String)
    {
        val event = Event()
        event.title = title
        event.date = date
        event.desc = description
        event.icon = icon
        event.priority = priority

        GlobalScope.launch {
            mDb?.eventDao()?.insert(event)
        }
    }

    fun retrieveMovies(): LiveData<List<Event>>
    {
        GlobalScope.launch {
            val list = mDb?.eventDao()?.getAll()
            allMovies.postValue(list)
        }
        return allMovies
    }

    fun deleteEvent(event : Event)
    {
        GlobalScope.launch {
            mDb?.eventDao()?.deleteEvent(event)
        }
    }
}