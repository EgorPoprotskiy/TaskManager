package com.egorpoprotskiy.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.egorpoprotskiy.taskmanager.navigation.TaskDestination
import com.egorpoprotskiy.taskmanager.ui.home.TaskListScreen
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
                    val navController = rememberNavController() // Получаем NavController

                    NavHost(
                        navController = navController,
                        startDestination = TaskDestination.HOME_ROUTE // Начальный экран
                    ) {
                        // Composable для экрана Home (список задач)
                        composable(TaskDestination.HOME_ROUTE) {
                            TaskListScreen(
                                // Передаем лямбду для навигации к экрану добавления задачи
                                navigateToTaskEntry = {
                                    navController.navigate(TaskDestination.TASK_ENTRY_ROUTE)
                                }
                            )
                        }
                        // Composable для экрана добавления задачи
                        composable(TaskDestination.TASK_ENTRY_ROUTE) {
                            TaskEntryScreen(
                                // Передаем лямбду для возврата на предыдущий экран
                                navigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}