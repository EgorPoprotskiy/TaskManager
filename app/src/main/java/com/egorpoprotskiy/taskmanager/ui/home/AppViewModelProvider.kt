package com.egorpoprotskiy.taskmanager.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.egorpoprotskiy.taskmanager.TaskApplication
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
            TaskEntryViewModel(
                taskApplication().container.taskRepository
            )
        }

        // Если появятся другие ViewModel, мы добавим их initializer здесь:
        // initializer {
        //     AnotherViewModel(
        //         this.createSavedStateHandle(),
        //         taskApplication().container.anotherRepository
        //     )
        // }
    }
}

fun CreationExtras.taskApplication(): TaskApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication)


