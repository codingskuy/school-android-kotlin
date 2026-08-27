@file:Suppress("DEPRECATION")

package io.codingskuy.todo.presentation.utils

import io.codingskuy.todo.domain.model.Task

/**
 * @deprecated Gunakan [io.codingskuy.todo.domain.usecase.TaskFormatterUseCase] instead.
 * File ini hanya untuk backward compatibility.
 */
@Deprecated(
    message = "Pindah ke domain layer. Gunakan TaskFormatterUseCase.",
    replaceWith = ReplaceWith(
        "TaskFormatterUseCase",
        "io.codingskuy.todo.domain.usecase.TaskFormatterUseCase"
    )
)
class TaskFormatter {
    fun format(task: Task): String = task.title.uppercase()
}
