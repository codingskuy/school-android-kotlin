package io.codingskuy.todo

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.codingskuy.todo.presentation.AddTaskScreen
import io.codingskuy.todo.presentation.CobaActivity
import io.codingskuy.todo.presentation.FragmentActivity
import io.codingskuy.todo.presentation.RecyclerActivity
import io.codingskuy.todo.presentation.TaskListScreen
import io.codingskuy.todo.presentation.ViewActivity
import io.codingskuy.todo.presentation.theme.ToDoTheme
import io.codingskuy.todo.presentation.viewmodel.ToDoViewModel
import io.codingskuy.todo.presentation.viewmodel.todoViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            ToDoTheme {
                val navController = rememberNavController()
                val todoVM: ToDoViewModel = viewModel(factory = todoViewModelFactory())
                val tasks by todoVM.tasks.collectAsState()
                val isSaving by todoVM.isSaving.collectAsState()


                NavHost(
                    navController = navController,
                    startDestination = "tasks"
                ) {
                    composable("tasks") {

                        TaskListScreen(
                            tasks = tasks,
                            modifier = Modifier.fillMaxSize(),
                            onAddClick = { navController.navigate("add") },
                            onDelete = { task ->
                                todoVM.deleteTask(task)
                            },
                            onItemTask = {
                                when (it.id) {
                                    1 -> context.startActivity(
                                        Intent(
                                            context,
                                            CobaActivity::class.java
                                        )
                                    )

                                    2 -> context.startActivity(
                                        Intent(
                                            context,
                                            ViewActivity::class.java
                                        )
                                    )

                                    3 -> context.startActivity(
                                        Intent(
                                            context,
                                            RecyclerActivity::class.java
                                        )
                                    )

                                    else -> context.startActivity(
                                        Intent(
                                            context,
                                            FragmentActivity::class.java
                                        )
                                    )
                                }
                            },
                            onCheckedChange = { todoVM.toggleDone(it) }
                        )
                    }
                    composable("add") {
                        val scope = rememberCoroutineScope()

                        Scaffold(
                            modifier = Modifier.fillMaxSize()
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier.padding(innerPadding).fillMaxWidth()
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