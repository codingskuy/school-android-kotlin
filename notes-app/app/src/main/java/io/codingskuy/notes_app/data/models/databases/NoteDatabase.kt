package io.codingskuy.notes_app.data.models.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import io.codingskuy.notes_app.data.models.schemas.NoteDao
import io.codingskuy.notes_app.data.models.schemas.NoteEntity

@Database(entities = [NoteEntity::class], version = 2, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}