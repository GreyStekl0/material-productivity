package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class NoteDaoTest : DatabaseTest() {
    @Test
    fun getNoteEntity_readsInsertedNote() =
        runTest {
            val note = testNoteEntity()

            noteDao.upsertNotes(listOf(note))

            assertEquals(note, noteDao.getNoteEntity(note.id).first())
        }

    @Test
    fun getPinnedToTodayNoteEntities_returnsOnlyPinnedNotes() =
        runTest {
            val pinnedNote = testNoteEntity(id = "pinned-note-id", isPinnedToToday = true)
            val unpinnedNote = testNoteEntity(id = "unpinned-note-id", isPinnedToToday = false)

            noteDao.upsertNotes(listOf(pinnedNote, unpinnedNote))

            assertEquals(
                listOf(pinnedNote),
                noteDao.getPinnedToTodayNoteEntities().first(),
            )
        }
}
