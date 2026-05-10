package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.testdoubles.TestNoteDao
import dev.stekl0.materialproductivity.core.data.testdoubles.TestProjectDao
import dev.stekl0.materialproductivity.core.data.testdoubles.testNoteEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testProjectEntity
import dev.stekl0.materialproductivity.core.database.dao.NoteDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfflineFirstNotesRepositoryTest {
    private lateinit var subject: OfflineFirstNotesRepository
    private lateinit var noteDao: NoteDao
    private lateinit var projectDao: ProjectDao

    @BeforeEach
    fun setup() {
        noteDao = TestNoteDao()
        projectDao = TestProjectDao()
        subject =
            OfflineFirstNotesRepository(
                noteDao = noteDao,
                projectDao = projectDao,
            )
    }

    @Test
    fun projectNotes_areOrderedByProjectNoteIds() =
        runTest {
            noteDao.upsertNotes(
                listOf(
                    testNoteEntity(id = "note-1", projectId = ProjectId),
                    testNoteEntity(id = "note-2", projectId = ProjectId),
                    testNoteEntity(id = "note-3", projectId = ProjectId),
                ),
            )
            projectDao.upsertProjects(
                listOf(
                    testProjectEntity(
                        id = ProjectId,
                        noteIdsJson = """["note-3","note-1"]""",
                    ),
                ),
            )

            assertEquals(
                listOf("note-3", "note-1"),
                subject.getProjectNotes(ProjectId).first().map { it.id },
            )
        }

    @Test
    fun pinnedToTodayNotes_returnsOnlyPinnedNotes() =
        runTest {
            noteDao.upsertNotes(
                listOf(
                    testNoteEntity(id = "pinned", isPinnedToToday = true),
                    testNoteEntity(id = "unpinned", isPinnedToToday = false),
                ),
            )

            assertEquals(
                listOf("pinned"),
                subject.getPinnedToTodayNotes().first().map { it.id },
            )
        }
}

private const val ProjectId = "project-id"
