package io.codingskuy.todo.domain.usecase

import io.codingskuy.todo.domain.model.Task
import io.codingskuy.todo.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.StateFlow

class GetTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(): StateFlow<List<Task>> = repository.task
}

class DeleteTaskUseCase(private val repository: TaskRepository) {
    operator fun invoke(taskId: Int) = repository.deleteTask(taskId)
}

class AddTaskUseCase(private val repository: TaskRepository) {
    operator fun invoke(title: String) = repository.addTask(title)
}

class ToggleDoneUseCase(private val repository: TaskRepository) {
    operator fun invoke(taskId: Int) = repository.toggleDone(taskId)
}