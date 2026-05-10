package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.model.asEntity
import dev.stekl0.materialproductivity.core.data.model.asExternalModel
import dev.stekl0.materialproductivity.core.data.model.orderedByIds
import dev.stekl0.materialproductivity.core.database.dao.NoteDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import dev.stekl0.materialproductivity.core.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

internal class OfflineFirstNotesRepository(
    private val noteDao: NoteDao,
    private val projectDao: ProjectDao,
) : NotesRepository {
    override fun getNotes(): Flow<List<Note>> =
        noteDao
            .getNoteEntities()
            .map { entities -> entities.map(NoteEntity::asExternalModel) }

    override fun getNote(noteId: String): Flow<Note?> =
        noteDao
            .getNoteEntity(noteId)
            .map { entity -> entity?.asExternalModel() }

    override fun getProjectNotes(projectId: String): Flow<List<Note>> =
        combine(
            projectDao.getProjectEntity(projectId).map { entity -> entity?.asExternalModel() },
            noteDao.getNoteEntities(),
        ) { project, notes ->
            notes
                .map(NoteEntity::asExternalModel)
                .orderedByIds(project?.noteIds.orEmpty(), Note::id)
        }

    override fun getInboxNotes(): Flow<List<Note>> =
        noteDao
            .getInboxNoteEntities()
            .map { entities -> entities.map(NoteEntity::asExternalModel) }

    override fun getPinnedToTodayNotes(): Flow<List<Note>> =
        noteDao
            .getPinnedToTodayNoteEntities()
            .map { entities -> entities.map(NoteEntity::asExternalModel) }

    override suspend fun upsertNotes(notes: List<Note>) {
        noteDao.upsertNotes(notes.map(Note::asEntity))
    }

    override suspend fun deleteNotes(ids: List<String>) {
        noteDao.deleteNotes(ids)
    }
}
