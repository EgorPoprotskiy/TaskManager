package com.egorpoprotskiy.taskmanager.ui.taskentry

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.copy
import com.egorpoprotskiy.taskmanager.R
import com.egorpoprotskiy.taskmanager.ui.home.AppViewModelProvider

//10 Создание экрана ввода новой задачи.
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEntryScreen(
    navigateBack: () -> Unit,
    taskEntryViewModel: TaskEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //Для доступа к конкретным значениям(titel и description) ОБЯЗАТЕЛЬНО использовать "by"
    val taskUiState by taskEntryViewModel.taskUiState.collectAsStateWithLifecycle()
    val isEntryValid by taskEntryViewModel.isEntryValid.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_new_task)) },
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Применяем отступы от Scaffold
                .padding(16.dp), // Дополнительный внутренний отступ для контента
            verticalArrangement = Arrangement.Center, // Выравниваем контент по центру по вертикали
            horizontalAlignment = Alignment.CenterHorizontally // Выравниваем контент по центру по горизонтали
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Отступы для колонки
            ) {
                OutlinedTextField(
                    value = taskUiState.title,
                    onValueChange = { newValue ->
                        taskEntryViewModel.updateUiState(taskUiState.copy(title = newValue))
                                    },
                    label = {Text(stringResource(R.string.add_title_label))},
                    placeholder = {Text(stringResource(R.string.add_placeholder))},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
//                Spacer(
//                    modifier = Modifier.padding(8.dp) // Отступ между полями ввода
//                )
                OutlinedTextField(
                    value = taskUiState.description,
                    onValueChange = { newValue ->
                        taskEntryViewModel.updateUiState(updateTask = taskUiState.copy(description = newValue))},
                    label = {Text(stringResource(R.string.add_description_task))},
                    placeholder = {Text(stringResource(R.string.add_placeholder))},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        taskEntryViewModel.saveTask()
                        navigateBack()
                    },
                    enabled = isEntryValid
                ) {
                    Text(stringResource(R.string.save_task))
                }
            }

        }

    }
}

@Preview
@Composable
fun TaskEntryScreePreview() {
    MaterialTheme {
        TaskEntryScreen(
            navigateBack = {}
        )
    }
}
