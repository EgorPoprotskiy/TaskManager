package com.egorpoprotskiy.taskmanager.navigation

//11 Создание навигации между экранами
// Создание NavHost происходит в MainActivity

/**
 * Объект, содержащий константы для всех навигационных маршрутов в приложении.
 */
object TaskDestination {
    const val HOME_ROUTE = "home_route" // Маршрут для экрана списка задач
    const val TASK_ENTRY_ROUTE = "taskEntry_route" // Маршрут для экрана добавления задачи
    //14 Добавление маршрута для экрана деталей.
    const val TASK_DETAIL_ROUTE = "taskDetail_route" // Маршрут для экрана деталей
}