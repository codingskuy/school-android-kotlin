package io.codingskuy.notes_app

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.codingskuy.notes_app.data.models.databases.NoteDatabase
import io.codingskuy.notes_app.data.models.schemas.NoteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NoteDaoTest {
    private lateinit var db: NoteDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetNotes() = runTest {
        val note = NoteEntity(
            title = "Belajar Room",
            content = "Migration & Test",
            date = 123L
        )

        db.noteDao().insert(note)

        val all = db.noteDao().getAllNotes().first()
        Assert.assertEquals(1, all.size)
        Assert.assertEquals("Belajar Room", all[0].title)
    }

    @Test
    fun deleteNote() = runTest {
        val note = NoteEntity(
            title = "Hapus",
            content = "ini",
            date = 123L
        )

        db.noteDao().insert(note)
        val inserted = db.noteDao().getAllNotes().first().first()

        db.noteDao().delete(inserted)
        val after = db.noteDao().getAllNotes().first()

        Assert.assertTrue(after.isEmpty())
    }

}