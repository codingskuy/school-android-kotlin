package io.codingskuy.notes_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.codingskuy.notes_app.domain.entities.Note
import io.codingskuy.notes_app.domain.usecase.DeleteNoteUseCase
import io.codingskuy.notes_app.domain.usecase.GetNoteByIdUseCase
import io.codingskuy.notes_app.domain.usecase.GetNotesUseCase
import io.codingskuy.notes_app.domain.usecase.InsertNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase
): ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote = _selectedNote.asStateFlow()

    init {
        viewModelScope.launch {
            getNotesUseCase().collect {
                _notes.value = it
            }
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            insertNoteUseCase(Note(title = title, content = content, date = System.currentTimeMillis()))
        }
    }

    fun removeNote(note: Note) = viewModelScope.launch {
        deleteNoteUseCase(note)
    }

    fun showDetailNote(id: Int) {
        viewModelScope.launch {
            _selectedNote.value = getNoteByIdUseCase(id)
        }
    }
}
