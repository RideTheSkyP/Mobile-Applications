package com.list3.todo

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//@Database(entities = [EventDBCreator::class], version = 1, exportSchema = false)
//abstract class EventDatabase : RoomDatabase()
//{
//    abstract val eventDatabaseDao: EventDatabaseDao
//
//    companion object                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
//    {
//        @Volatile
//        private var INSTANCE: EventDatabase? = null
//
//        fun getInstance(context: Context): EventDatabase
//        {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        EventDatabase::class.java,
//                        "event_database"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}

@Database(entities = [EventDBCreator::class], version = 1)
abstract class EventDatabase : RoomDatabase()
{
    abstract fun eventDao(): EventDatabaseDao

    companion object
    {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase
        {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
//                    .addCallback(EventDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class EventDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback()
        {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase)
            {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.eventDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        fun populateDatabase(eventDao: EventDatabaseDao)
        {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
//            eventDao.deleteAll()

            val word = EventDBCreator(
                priority = 1,
                icon = 1,
                date = "date",
                title = "title",
                desc = "desc"
            )
//            AsyncTask.execute { // Insert Data
//                eventDao.insert(word)
//            }
            eventDao.insert(word)
//            word = EventDBCreator("World!")
//            eventDao.insert(word)
        }
    }
}