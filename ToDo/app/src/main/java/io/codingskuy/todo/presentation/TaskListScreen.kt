package io.codingskuy.todo.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.presentation.utils.TaskFormatter

@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onDelete: (Task) -> Unit,
    tasks: List<Task>,
    onItemTask: (Task) -> Unit,
    onCheckedChange: (Task) -> Unit,
    formatter: TaskFormatter
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) {  innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding)
        ) {
            items(tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onItemTask(task) }),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Checkbox(
                        checked = task.done,
                        onCheckedChange = { onCheckedChange(task) },
                        checkmarkStroke = Stroke(),
                        outlineStroke = Stroke(),
                    )
                    Text(
                        text = formatter.format(task),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(onClick = { onDelete(task) }) {
                        Text("Sampah")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    _root_ide_package_.io.codingskuy.todo.presentation.theme.ToDoTheme {
        TaskListScreen(
            onAddClick = { },
            onDelete = { },
            tasks = mutableListOf(),
            onItemTask = {},
            formatter = TaskFormatter(),
            onCheckedChange = {},
        )
    }
}