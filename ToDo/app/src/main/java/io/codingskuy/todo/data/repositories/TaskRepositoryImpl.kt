package io.codingskuy.todo.data.repositories

import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object TaskRepositoryImpl : TaskRepository {
    private val _tasks = MutableStateFlow(
        listOf(
            Task(1, "Belajar Jetpack Compose"),
            Task(2, "Bikin To-Do App"),
            Task(3, "Refactor Kalkulator Tip"),
            Task(4, "Push ke Github"),
            Task(5, "Bikin portofolio"),
        )
    )

    override val task: StateFlow<List<Task>> = _tasks.asStateFlow()

    override fun addTask(title: String) {
        val newId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
        _tasks.value = _tasks.value + Task(newId, title)
    }

    override fun deleteTask(taskId: Int) {
        _tasks.value = _tasks.value.filterNot { it.id == taskId }
    }

    override fun toggleDone(taskId: Int) {
        _tasks.value = _tasks.value.map {
            if(it.id == taskId) it.copy(done = !it.done) else it
        }
    }
}