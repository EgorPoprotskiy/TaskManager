package com.egorpoprotskiy.taskmanager.navigation

//11 Создание навигации между экранами
// Создание NavHost происходит в MainActivity

/**
 * Объект, содержащий константы для всех навигационных маршрутов в приложении.
 */
object TaskDestination {
    const val HOME_ROUTE = "home" // Маршрут для экрана списка задач
    const val TASK_ENTRY_ROUTE = "task_entry" // Маршрут для экрана добавления задачи
}