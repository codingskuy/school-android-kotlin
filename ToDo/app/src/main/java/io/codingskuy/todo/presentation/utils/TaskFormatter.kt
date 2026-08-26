package io.codingskuy.todo.presentation.utils

import io.codingskuy.todo.domain.model.Task

class TaskFormatter {
    fun format(task: Task): String = task.title.uppercase()
}