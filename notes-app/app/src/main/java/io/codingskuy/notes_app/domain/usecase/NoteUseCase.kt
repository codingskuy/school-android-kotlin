package io.codingskuy.notes_app.domain.usecase

import io.codingskuy.notes_app.domain.entities.Note
import io.codingskuy.notes_app.domain.repositories.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(val repo: NoteRepository) {
    operator fun invoke() = repo.getAllNotes()
}
class InsertNoteUseCase @Inject constructor(val repo: NoteRepository) {
    suspend operator fun invoke(note: Note) = repo.insert(note)
}
class DeleteNoteUseCase @Inject constructor(val repo: NoteRepository) {
    suspend operator fun invoke(note: Note) = repo.delete(note)
}
class GetNoteByIdUseCase @Inject constructor(val repo: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? = repo.getNoteById(id)
}