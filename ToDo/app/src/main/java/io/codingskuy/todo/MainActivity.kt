package io.codingskuy.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.codingskuy.todo.presentation.AddTaskScreen
import io.codingskuy.todo.presentation.TaskListScreen
import io.codingskuy.todo.presentation.theme.ToDoTheme
import io.codingskuy.todo.presentation.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                val navController = rememberNavController()
                val todoVM: ToDoViewModel = hiltViewModel()
                val tasks by todoVM.tasks.collectAsState()
                val isSaving by todoVM.isSaving.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = "tasks"
                ) {
                    composable("tasks") {
                        TaskListScreen(
                            tasks = tasks,
                            formatter = todoVM.formatter,
                            modifier = Modifier.fillMaxSize(),
                            onAddClick = { navController.navigate("add") },
                            onDelete = { task -> todoVM.deleteTask(task) },
                            onItemTask = { /* TODO: Navigate to detail screen */ },
                            onCheckedChange = { todoVM.toggleDone(it) }
                        )
                    }
                    composable("add") {
                        val scope = rememberCoroutineScope()

                        Scaffold(
                            modifier = Modifier.fillMaxSize()
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSaving) {
                                    Text("Sedang Menyimpan...")
                                } else {
                                    AddTaskScreen(
                                        onSave = { title ->
                                            scope.launch {
                                                todoVM.addTask(title)
                                                navController.popBackStack()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
