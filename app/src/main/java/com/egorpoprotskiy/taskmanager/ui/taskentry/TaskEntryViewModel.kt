package com.egorpoprotskiy.taskmanager.ui.taskentry

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//12 Создание ViewModel для экрана ввода задачи.
class TaskEntryViewModel(
    val taskRepository: TaskRepository,
    private val taskId: Long? = null //Добавляем опциональный ID
) : ViewModel() {
    // Состояние, которое хранит данные формы
    private val _taskUiState = MutableStateFlow(Task())
    val taskUiState: StateFlow<Task> = _taskUiState.asStateFlow()

    // Состояние, которое хранит валидность формы
    private val _isEntryValid = MutableStateFlow(false)
    val isEntryValid: StateFlow<Boolean> = _isEntryValid.asStateFlow()

    init {
        // Загружаем задачу, если она существует
        if (taskId != null && taskId > 0L) {
            viewModelScope.launch {
                _taskUiState.value = taskRepository.getTaskById(taskId)
                    .filterNotNull() //Убеждаемся, что мы не получим null из потока.
                    .first() //Получаем первый элемент из потока и завершаем его.
            }
        }
    }
    // Обновляет состояние формы UI
    fun updateUiState(updateTask: Task) {
        _taskUiState.value = updateTask
        _isEntryValid.value = validateInput(updateTask)
    }

    fun saveTask() {
        viewModelScope.launch {
            if (taskUiState.value.id > 0) {
                taskRepository.updateTask(taskUiState.value)
            } else {
                taskRepository.insertTask(taskUiState.value)
            }
        }
    }
    // Проверяет, является ли ввод валидным
    fun validateInput(task: Task): Boolean {
        return task.title.isNotBlank()
    }
}