package com.egorpoprotskiy.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//1 Созданние класса данных.

//Корректно указывает Room, что это сущность базы данных и как назвать таблицу.
//Чтобы Room распознал класс как таблицу базы данных, тебе понадобится аннотация @Entity над классом.
@Entity(tableName = "tasks")
data class Task (
    //Чтобы Room автоматически генерировал значения для первичного ключа, используй параметр autoGenerate = true.
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String = "",
    val description: String? = "",
    val isCompleted: Boolean = false
)