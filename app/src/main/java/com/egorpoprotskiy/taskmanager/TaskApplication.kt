package com.egorpoprotskiy.taskmanager

import android.app.Application
import com.egorpoprotskiy.taskmanager.di.AppContainer
import com.egorpoprotskiy.taskmanager.di.DefaultAppContainer

//6 Создание TaskApplication (Application Class).
//Создается в корневом пакете.
/**
класс Application является базовым классом для поддержания глобального состояния приложения.
 */
class TaskApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}

//Обязательно зарегать TaskApplication в манифесте.