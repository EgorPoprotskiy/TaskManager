package com.egorpoprotskiy.taskmanager.navigation

//11 Создание навигации между экранами
// Создание NavHost происходит в MainActivity

/**
 * Объект, содержащий константы для всех навигационных маршрутов в приложении.
 */
object TaskDestination {
    const val HOME_ROUTE = "home" // Маршрут для экрана списка задач
    const val TASK_ENTRY_ROUTE = "task_entry" // Маршрут для экрана добавления задачи
    //14 Добавление маршрута для экрана деталей.
    const val TASK_DETAIL_ROUTE = "task_detail" // Маршрут для экрана деталей
}