package io.codingskuy.todo.domain.usecase

import io.codingskuy.todo.domain.model.Task

/**
 * Use Case: Format task title untuk ditampilkan ke user.
 *
 * Kenapa di domain layer?
 * Karena formatting title = transformasi data = bisnis logic.
 * Domain paham "bagaimana data harus ditampilkan".
 *
 * Contoh: task.title = "Belajar Compose" → "BELAJAR COMPOSE"
 */
class TaskFormatterUseCase {
    fun format(task: Task): String = task.title.uppercase()
}
