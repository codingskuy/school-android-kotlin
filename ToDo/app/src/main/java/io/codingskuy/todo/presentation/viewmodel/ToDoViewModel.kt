@file:Suppress("UNCHECKED_CAST")

package io.codingskuy.todo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.codingskuy.todo.data.repositories.TaskRepositoryImpl
import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.domain.usecase.AddTaskUseCase
import io.codingskuy.todo.domain.usecase.DeleteTaskUseCase
import io.codingskuy.todo.domain.usecase.GetTasksUseCase
import io.codingskuy.todo.domain.usecase.ToggleDoneUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.Duration.Companion.milliseconds

class ToDoViewModel(
    getTask: GetTasksUseCase,
    private val addTasks: AddTaskUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val toggleDone: ToggleDoneUseCase
) : ViewModel() {
    val tasks: StateFlow<List<Task>> = getTask()
    private val TAG: String = "TODO_VM"

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    suspend fun addTask(title: String) {
        _isSaving.value = true
        Log.d(TAG, "addTask: Mulai Menyimpan")
        delay(1000.milliseconds)

        addTasks(title)

        _isSaving.value = false
        Log.d(TAG, "addTask: Selesai Tersimpan")

    }

    fun deleteTask(task: Task) = deleteTask(task.id)
    fun toggleDone(task: Task) = toggleDone(task.id)
}

fun todoViewModelFactory(): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ToDoViewModel(
                getTask = GetTasksUseCase(TaskRepositoryImpl),
                addTasks = AddTaskUseCase(TaskRepositoryImpl),
                deleteTask = DeleteTaskUseCase(TaskRepositoryImpl),
                toggleDone = ToggleDoneUseCase(TaskRepositoryImpl)
            ) as T
        }
    }
}