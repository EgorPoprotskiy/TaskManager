package com.egorpoprotskiy.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.egorpoprotskiy.taskmanager.navigation.TaskDestination
import com.egorpoprotskiy.taskmanager.ui.home.TaskListScreen
import com.egorpoprotskiy.taskmanager.ui.taskdetail.TaskDetailScreen
import com.egorpoprotskiy.taskmanager.ui.taskentry.TaskEntryScreen
import com.egorpoprotskiy.taskmanager.ui.theme.TaskManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskApp()
                }
            }
        }
    }
}

@Composable
fun TaskApp() {
    // 11 Создаем навигацию.
    val navController = rememberNavController() // Получаем NavController

    NavHost(
        navController = navController,
        startDestination = TaskDestination.HOME_ROUTE // Начальный экран
    ) {
        // Composable для экрана Home (список задач)
        composable(TaskDestination.HOME_ROUTE) {
            TaskListScreen(
                // Передаем лямбду для навигации к экрану добавления задачи(создается в конструкторе класса TaskListScreen)
                navigateToTaskEntry = {
                    navController.navigate(TaskDestination.TASK_ENTRY_ROUTE)
                },
                // 14 Передаем лямбду для навигации к экрану деталей задачи(создается в конструкторе класса TaskListScreen)
                navigateToTaskDetail = { taskId ->
                    navController.navigate("${TaskDestination.TASK_DETAIL_ROUTE}/$taskId")
                }
            )
        }
        // Composable для экрана добавления задачи
        composable(
            route = "${TaskDestination.TASK_ENTRY_ROUTE}?${TaskEntryScreen.TASK_ID_ARG}={${TaskEntryScreen.TASK_ID_ARG}}",
            arguments = listOf(navArgument(TaskEntryScreen.TASK_ID_ARG) {
                type = NavType.LongType
                defaultValue = 0L
            })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong(TaskEntryScreen.TASK_ID_ARG) ?: 0L
            TaskEntryScreen(
                // Передаем лямбду для возврата на предыдущий экран(создается в конструкторе класса TaskEntryScreen)
                navigateBack = { navController.popBackStack() },
                taskId = taskId
            )
        }
        // 14 Composable для экрана деталей.
        composable(
            route = "${TaskDestination.TASK_DETAIL_ROUTE}/{${TaskDetailScreen.TASK_ID_ARG}}",
            arguments = listOf(navArgument(TaskDetailScreen.TASK_ID_ARG) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            // Получаем taskId из аргументов
            val taskId = backStackEntry.arguments?.getLong(TaskDetailScreen.TASK_ID_ARG) ?: 0L
            TaskDetailScreen(
                taskId = taskId,
                navigateBack = { navController.popBackStack() },
                navigateToEditTask = { taskIdToEdit ->
                    navController.navigate("${TaskDestination.TASK_ENTRY_ROUTE}?${TaskEntryScreen.TASK_ID_ARG}=$taskIdToEdit")
                }
            )
        }
    }
}
