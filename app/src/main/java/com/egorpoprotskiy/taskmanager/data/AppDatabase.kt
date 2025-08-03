package com.egorpoprotskiy.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//3 Создание БД.

//абстрактный класс, который представляет базу данных Room.
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    //Это гарантирует, что у тебя будет только один экземпляр базы данных во всем приложении.
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // Создаем базу данных только если INSTANCE == null
                val instance = Room.databaseBuilder(
                    /*Важно использовать applicationContext при создании базы данных, чтобы избежать
                    утечек памяти, связанных с жизненным циклом Activity.*/
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_manager_database"
                )
//                    .allowMainThreadQueries()
                    .build()
                /*
                Это самое важное изменение. После того как база данных создана внутри synchronized блока,
                её экземпляр присваивается переменной INSTANCE. Это гарантирует, что при последующих
                вызовах getInstance будет возвращаться уже существующий экземпляр, а не будет создаваться новый.
                 */
                INSTANCE = instance
                instance
            }
        }
    }
}
