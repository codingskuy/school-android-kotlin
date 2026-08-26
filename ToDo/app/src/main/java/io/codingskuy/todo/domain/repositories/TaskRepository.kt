package io.codingskuy.todo.domain.repositories

import io.codingskuy.todo.domain.model.Task
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>>
    fun addTask(title: String)
    fun deleteTask(taskId: Int)
    fun toggleDone(taskId: Int)
}