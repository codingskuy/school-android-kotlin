package io.codingskuy.todo.domain.model

data class Task(
    val id: Int,
    val title: String,
    val done: Boolean = false
)