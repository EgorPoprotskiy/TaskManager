package com.egorpoprotskiy.taskmanager.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.egorpoprotskiy.taskmanager.TaskApplication
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.ui.taskdetail.TaskDetailScreen
import com.egorpoprotskiy.taskmanager.ui.taskdetail.TaskDetailViewModel
import com.egorpoprotskiy.taskmanager.ui.taskentry.TaskEntryScreen
import com.egorpoprotskiy.taskmanager.ui.taskentry.TaskEntryViewModel

//8 Создание AppViewModelProvider (ViewModel Factory)

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            // Получаем TaskRepository из AppContainer нашего TaskApplication
            TaskViewModel(
                taskApplication().container.taskRepository
            )
        }
        // INITIALIZER ДЛЯ TaskEntryViewModel
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            val taskId = savedStateHandle.get<Long?>(TaskEntryScreen.TASK_ID_ARG)
            TaskEntryViewModel(
                taskApplication().container.taskRepository,
                taskId = taskId
            )
        }
        initializer {
            val savedStateHandle = this.createSavedStateHandle()
            val taskId = savedStateHandle.get<Long>(TaskDetailScreen.TASK_ID_ARG) ?: 0L
            TaskDetailViewModel(
                taskApplication().container.taskRepository,
                taskId = taskId
            )
        }
    }

}

fun CreationExtras.taskApplication(): TaskApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication)


