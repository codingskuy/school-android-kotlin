package io.codingskuy.notes_app.data.repositories

import io.codingskuy.notes_app.data.models.schemas.NoteDao
import io.codingskuy.notes_app.data.models.schemas.NoteEntity
import io.codingskuy.notes_app.domain.entities.Note
import io.codingskuy.notes_app.domain.repositories.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val dao: NoteDao): NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> = dao.getAllNotes().map { list -> list.map { Note(
        id = it.id,
        title = it.title,
        content = it.content,
        date = it.date,
        priority = it.priority
    ) } }

    override suspend fun insert(note: Note) = dao.insert(NoteEntity(note.id, note.title, note.content, note.date))

    override suspend fun delete(note: Note) = dao.delete(NoteEntity(
        id = note.id,
        title = note.title,
        content = note.content,
        date = note.date,
        priority = note.priority
    ))

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)?.let {
        Note(
            id = it.id,
            title = it.title,
            content = it.content,
            date = it.date,
            priority = it.priority
        )
    }
}