@file:Suppress("UNCHECKED_CAST")

package io.codingskuy.todo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.domain.usecase.AddTaskUseCase
import io.codingskuy.todo.domain.usecase.DeleteTaskUseCase
import io.codingskuy.todo.domain.usecase.GetTasksUseCase
import io.codingskuy.todo.domain.usecase.ToggleDoneUseCase
import io.codingskuy.todo.presentation.utils.TaskFormatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ToDoViewModel @Inject constructor(
    getTask: GetTasksUseCase,
    private val addTasks: AddTaskUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val toggleDone: ToggleDoneUseCase,
    val formatter: TaskFormatter
) : ViewModel() {
    private val TAG: String = "TODO_VM"

    val tasks: StateFlow<List<Task>> = getTask()

    //    val formatedTask : StateFlow<List<Task>> = getTask()
    //        .map {
    //            task -> task.map {
    //                it.copy(it.id, formatter.format(it), it.done)
    //            }
    //        }
    //        .stateIn(
    //            scope = viewModelScope,
    //            started = SharingStarted.WhileSubscribed(5000),
    //            initialValue = emptyList()
    //        )

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


/// Unused because manual factory builder was replace by hilt injection management
/*
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
}*/
