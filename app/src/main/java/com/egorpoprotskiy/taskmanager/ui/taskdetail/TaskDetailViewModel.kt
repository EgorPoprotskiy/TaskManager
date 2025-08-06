package com.egorpoprotskiy.taskmanager.ui.taskdetail

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel (
    val taskRepository: TaskRepository,
    val taskId: Long
): ViewModel() {
    private val _taskUiState = MutableStateFlow(Task())
    val taskUiState: StateFlow<Task> = _taskUiState.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getTaskById(taskId).collect { task ->
            _taskUiState.value = task ?: Task()
            }
        }
    }
}