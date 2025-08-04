package com.egorpoprotskiy.taskmanager.ui.taskentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskEntryViewModel(val taskRepository: TaskRepository): ViewModel() {
    private val _taskUiState = MutableStateFlow(Task())
    val taskUiState: StateFlow<Task> = _taskUiState.asStateFlow()
    private val _isEntryValid = MutableStateFlow(false)
    val isEntryValid: StateFlow<Boolean> = _isEntryValid.asStateFlow()

    fun updateUiState(updateTask: Task) {
        _taskUiState.value = updateTask
        _isEntryValid.value = validateInput(updateTask)
    }
    fun saveTask() {
        viewModelScope.launch {
            taskRepository.insertTask(_taskUiState.value)
        }
    }
    fun validateInput(task: Task): Boolean {
        return task.title.isNotBlank()
    }
}