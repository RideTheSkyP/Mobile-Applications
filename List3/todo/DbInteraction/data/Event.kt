package com.list3.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event (
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "priority") val priority: Int = 0,
    @ColumnInfo(name = "icon") val icon: Int = 0,
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "event_title") var title: String = "",
    @ColumnInfo(name = "event_desc") val desc: String = ""
)