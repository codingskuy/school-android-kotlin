package io.codingskuy.notes_app.domain.entities

data class Note(
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: Long
)
