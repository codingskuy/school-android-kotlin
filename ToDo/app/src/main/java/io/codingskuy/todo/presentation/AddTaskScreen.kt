package io.codingskuy.todo.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddTaskScreen(
    onSave: (String) -> Unit) {
    var newTask by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = newTask,
            onValueChange = {
                newTask = it
            },

            )
        Button(onClick = { onSave(
            newTask
        ) }) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    _root_ide_package_.io.codingskuy.todo.presentation.theme.ToDoTheme() {
        AddTaskScreen(onSave = {})
    }
}