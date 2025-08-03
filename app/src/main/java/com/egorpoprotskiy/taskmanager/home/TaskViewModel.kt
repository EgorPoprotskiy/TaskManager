package com.egorpoprotskiy.taskmanager.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//5 Создание ViewModel для управления состоянием экрана списка задач.
/*
ViewModel — отвечает за предоставление данных для UI и за управление изменениями конфигурации
 */
class TaskViewModel(val taskRepository: TaskRepository): ViewModel() {
    // Свойство для получения всех задач в виде StateFlow
    val taskUiState: StateFlow<TaskUiState> = taskRepository.getAllTasks()
        .map { tasks -> TaskUiState(tasks) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = TaskUiState()
        )
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}
/**
 * Класс состояния UI для экрана списка задач.
 * Содержит список задач, который будет отображаться.
 */
data class TaskUiState(val taskList: List<Task> = listOf())