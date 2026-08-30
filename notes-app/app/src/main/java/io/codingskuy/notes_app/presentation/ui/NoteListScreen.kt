package io.codingskuy.notes_app.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.codingskuy.notes_app.domain.entities.Note
import io.codingskuy.notes_app.presentation.viewmodel.NoteViewModel

@Composable
fun NoteListScreen(
    viewModel: NoteViewModel,
    onAddClick: () -> Unit = {},
    onNoteClick: (Note) -> Unit = {}
) {
    val notes by viewModel.note.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Catatan Harian", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        if (notes.isEmpty()) {
            Text("Belum ada catatan. Tambah yang pertama!", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(notes, key = { it.id }) { note ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        onClick = { onNoteClick(note) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(note.title, style = MaterialTheme.typography.titleMedium)
                            Text(note.content, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                        }
                    }
                }
            }
        }

        Button(onClick = onAddClick, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text("Tambah Catatan")
        }
    }
}
