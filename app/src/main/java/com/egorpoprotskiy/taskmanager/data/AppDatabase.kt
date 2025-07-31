package com.egorpoprotskiy.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//3
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_manager_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
