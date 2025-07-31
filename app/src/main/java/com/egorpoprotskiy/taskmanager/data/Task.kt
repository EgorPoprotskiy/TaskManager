package com.egorpoprotskiy.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Корректно указывает Room, что это сущность базы данных и как назвать таблицу.
@Entity(tableName = "tasks")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String?,
    val isCompleted: Boolean = false
)