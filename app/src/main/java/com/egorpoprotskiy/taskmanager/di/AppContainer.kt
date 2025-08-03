package com.egorpoprotskiy.taskmanager.di

import android.content.Context
import com.egorpoprotskiy.taskmanager.data.AppDatabase
import com.egorpoprotskiy.taskmanager.data.OfflineTaskRepository
import com.egorpoprotskiy.taskmanager.data.TaskRepository

//5 Создание AppContainer
/**
AppContainer будет отвечать за создание всех зависимостей,
которые нужны нашему приложению (например, база данных и репозиторий).
 */
//Интерфейс AppContainer: Определяет, какие зависимости будут предоставлены.
interface AppContainer {
    val taskRepository: TaskRepository
}
//DefaultAppContainer: Реализует этот интерфейс.
class DefaultAppContainer(private val context: Context): AppContainer {
    //by lazy гарантирует, что taskRepository будет инициализирован только при первом обращении к нему, что эффективно с точки зрения ресурсов.
    override val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(
            //используй TaskDao для создания экземпляра OfflineTaskRepository.
            AppDatabase
                //Сначала получи экземпляр AppDatabase с помощью его синглтон-метода getInstance().
                .getInstance(context)
                //Затем из AppDatabase получи TaskDao.
                .taskDao()
        )
    }
}