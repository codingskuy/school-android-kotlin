package io.codingskuy.notes_app

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.codingskuy.notes_app.data.models.databases.MIGRATION_1_2
import io.codingskuy.notes_app.data.models.databases.NoteDatabase
import io.codingskuy.notes_app.data.models.schemas.NoteDao
import io.codingskuy.notes_app.data.repositories.NoteRepositoryImpl
import io.codingskuy.notes_app.domain.repositories.NoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(context,
        NoteDatabase::class.java, "notes_db")
            .addMigrations(MIGRATION_1_2)
            .build()

    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository = NoteRepositoryImpl(dao)
}