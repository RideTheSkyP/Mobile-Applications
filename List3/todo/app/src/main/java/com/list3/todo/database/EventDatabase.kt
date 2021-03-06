package com.list3.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1)
abstract class EventDatabase : RoomDatabase()
{
    abstract fun eventDao(): EventDao

    companion object
    {
        private var INSTANCE: EventDatabase? = null

        fun getInstance(context: Context): EventDatabase?
        {
            if (INSTANCE == null)
            {
                synchronized(EventDatabase::class)
                {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        EventDatabase::class.java, "event").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance()
        {
            INSTANCE = null
        }
    }
}