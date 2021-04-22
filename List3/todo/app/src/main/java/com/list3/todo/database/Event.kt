package com.list3.todo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event (
        @PrimaryKey(autoGenerate = true) var id: Int? = null,
        @ColumnInfo(name = "priority") var priority: Int = 0,
        @ColumnInfo(name = "icon") var icon: Int = 0,
        @ColumnInfo(name = "date") var date: String = "",
        @ColumnInfo(name = "event_title") var title: String = "",
        @ColumnInfo(name = "event_desc") var desc: String = ""
)