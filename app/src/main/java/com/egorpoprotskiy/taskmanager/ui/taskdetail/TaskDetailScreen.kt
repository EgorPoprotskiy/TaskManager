package com.egorpoprotskiy.taskmanager.ui.taskdetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.egorpoprotskiy.taskmanager.R
import com.egorpoprotskiy.taskmanager.data.Task
import com.egorpoprotskiy.taskmanager.ui.home.AppViewModelProvider

object TaskDetailScreen {
    const val TASK_ID_ARG = "taskId"
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskDetailScreen(
    taskId: Long,
    navigateBack: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val taskUiState by taskDetailViewModel.taskUiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail_task)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                // navigationIcon ДЛЯ КНОПКИ "НАЗАД"
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
//                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
//                    onClick = { Log.d("TaskDetailScreen", "Нажата кнопка удаления задачи")},
                    onClick = {
                        showDeleteDialog = true
//                        navigateBack()
                    },
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_button)
                    )
                }
                FloatingActionButton(
                    onClick = { Log.d("TaskDetailScreen", "Нажата кнопка редактирования задачи")},
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.edit_task)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = taskUiState.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider(thickness = 1.dp)
            Text(
                text = taskUiState.description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                // Закрываем диалог при нажатии вне его или кнопки "Назад"
                showDeleteDialog = false
            },
            //заголовок диалогового окна
            title = { Text(stringResource(R.string.delete_confirmation_title)) },
            //текст диалогового окна
            text = { Text(stringResource(R.string.delete_confirmation_message)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Подтверждаем удаление и закрываем диалог
                        taskDetailViewModel.deleteTask() //вызываем функцию удаления
                        navigateBack() //возвращаемся на предыдущий экран
                        showDeleteDialog = false
                    }
                ) {
                    Text(stringResource(R.string.delete_button))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Отменяем удаление и закрываем диалог
                        showDeleteDialog = false
                    }
                ) {
                    Text(stringResource(R.string.cancel_button))
                }
            }
        )
    }
}

@Preview
@Composable
fun TaskDetailScreenPreview() {
    MaterialTheme {
        TaskDetailScreen(
            taskId = 1L,
            navigateBack = {}
        )
    }
}