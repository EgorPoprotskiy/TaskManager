package com.egorpoprotskiy.taskmanager.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.egorpoprotskiy.taskmanager.R
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.data.TaskRepository
import com.egorpoprotskiy.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

//9 Создание пользовательского интерфейса.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Собираем состояние UI из ViewModel.
    // collectAsStateWithLifecycle более предпочтителен, так как он собирает Flow только
    // когда UI находится в активном состоянии (STARTED, RESUMED).
    val taskUiState by taskViewModel.taskUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.application_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues -> // paddingValues содержит отступы от TopAppBar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Применяем отступы от TopAppBar
        ) {
            if (taskUiState.taskList.isEmpty()) {
                // Отображаем сообщение, если список задач пуст
                Text(
                    text = stringResource(R.string.add_new_task),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Отображаем список задач с помощью LazyColumn
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = 8.dp,
                        bottom = 8.dp
                    ), // Отступы сверху и снизу списка
                    verticalArrangement = Arrangement.spacedBy(4.dp) // Промежутки между элементами списка
                ) {
                    items(items = taskUiState.taskList, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onTaskClick = { clickedTask ->
                                Log.d("TaskListScreen", "Нажата задача: ${clickedTask.title}")
                                // TODO: Добавить логику навигации к деталям задачи
                            },
                            onToggleComplete = { taskToUpdate ->
                                // Обновляем статус задачи через ViewModel
                                taskViewModel.updateTask(taskToUpdate.copy(isCompleted = !taskToUpdate.isCompleted))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onTaskClick: (Task) -> Unit,
    onToggleComplete: (Task) -> Unit
) {
    Card(
        modifier = modifier
            //Заставляет карточку занимать всю доступную ширину.
            .fillMaxWidth()
            //Добавляет внешний отступ (маржин) вокруг каждой карточки, чтобы они не сливались.
            .padding(vertical = 4.dp, horizontal = 8.dp)
            //Делает всю область карточки кликабельной.
            .clickable { onTaskClick(task) },
        //Придаёт карточке небольшую тень, делая её более объёмной.
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            //Внутренний отступ (паддинг) для содержимого внутри карточки.
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            //Выравнивает дочерние элементы (Text и Checkbox) по центру по вертикали внутри Row.
            verticalAlignment = Alignment.CenterVertically,
            //Распределяет элементы так, чтобы они занимали максимально возможное пространство, при этом Text будет слева, а Checkbox — справа.
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                ),
                //Позволяет тексту занимать всю доступную горизонтальную ширину, "выталкивая" чекбокс вправо.
                modifier = Modifier.weight(1f)
            )
            Checkbox(
                //Состояние чекбокса напрямую привязано к свойству isCompleted объекта task.
                checked = task.isCompleted,
                //Когда пользователь нажимает на чекбокс, вызывается лямбда onToggleComplete.
                onCheckedChange = { onToggleComplete(task) }
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    val task = Task(
        1,
        "Заголовок",
        "Описание",
        false
    )
    TaskItem(
        task = task,
        onTaskClick = {},
        onToggleComplete = {}
    )
}

// Предпросмотр для Android Studio
@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    TaskManagerTheme {
        TaskListScreen(taskViewModel = previewTaskViewModel) // Использование ViewModel для предпросмотра
    }
}

// Вспомогательный ViewModel для предпросмотра (для отладки)
val previewTaskViewModel = object : TaskViewModel(
    object : TaskRepository {
        override suspend fun insertTask(task: Task) {}
        override suspend fun updateTask(task: Task) {}
        override suspend fun deleteTask(task: Task) {}
        override fun getAllTasks(): Flow<List<Task>> = flowOf(
            listOf(
                Task(1, "Купить молоко", "",false),
                Task(2, "Заплатить счета", "",true),
                Task(3, "Позвонить другу", "",false)
            )
        )
    }
) {}