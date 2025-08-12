package com.egorpoprotskiy.taskmanager.data

import kotlinx.coroutines.flow.Flow

//4 Создание оффлайн репозитория.

class OfflineTaskRepository(private val taskDao: TaskDao): TaskRepository {
    override suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }
    override suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }
    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }
    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override fun getTaskById(taskId: Long): Flow<Task?> {
        return taskDao.getTaskById(taskId)
    }
}