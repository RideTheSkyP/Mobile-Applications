package com.example.arkanoid

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Events")
data class EventDBCreator(
    @PrimaryKey(autoGenerate=true) val id: Int = 0,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "icon") val icon: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "event_title") val title: String,
    @ColumnInfo(name = "event_desc") val desc: String)