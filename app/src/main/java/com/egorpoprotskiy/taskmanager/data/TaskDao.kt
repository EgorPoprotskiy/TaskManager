package com.egorpoprotskiy.taskmanager.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//2 Создание интерфейса для общения с БД.

//DAO — это интерфейс, который Room использует для взаимодействия с твоей базой данных.
@Dao
//Функции вставки, обновления и удаления могут быть suspend функциями, так как они будут выполняться асинхронно.
interface TaskDao {
    //Если задача с таким же первичным ключом уже существует, она будет проигнорирована.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)
    @Update
    suspend fun update(task: Task)
    @Delete
    suspend fun delete(task: Task)
    @Query("SELECT * FROM tasks")
    //Эта функция должна возвращать Flow<List<Task>>, чтобы UI мог реагировать на изменения в базе данных в реальном времени.
    fun getAllTasks(): Flow<List<Task>>
    @Query("SELECT * FROM tasks WHERE id = :id")
    //Функция получения одного элемента
    fun getTaskById(id: Long): Flow<Task?>
}