package io.codingskuy.notes_app.domain.repositories

import io.codingskuy.notes_app.domain.entities.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    suspend fun insert(note: Note)
    suspend fun delete(note: Note)
    suspend fun getNoteById(id: Int): Note?
}