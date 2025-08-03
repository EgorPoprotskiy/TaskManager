package com.egorpoprotskiy.taskmanager.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.egorpoprotskiy.taskmanager.TaskApplication

//8 Создание AppViewModelProvider (ViewModel Factory)

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            // Получаем TaskRepository из AppContainer нашего TaskApplication
            TaskViewModel(
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


