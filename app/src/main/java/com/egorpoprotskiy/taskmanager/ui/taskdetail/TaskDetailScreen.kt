package com.egorpoprotskiy.taskmanager.ui.taskdetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egorpoprotskiy.taskmanager.R

object TaskDetailScreen {
    const val TASK_ID_ARG = "taskId"
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskDetailScreen(
    taskId: Long,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
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
//                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
            ) {
                FloatingActionButton(
                    onClick = { Log.d("TaskDetailScreen", "Нажата кнопка удаления задачи")},
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
    ) {

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