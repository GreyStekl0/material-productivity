package dev.stekl0.materialproductivity.core.data.testdoubles

import dev.stekl0.materialproductivity.core.database.dao.NoteDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectFolderDao
import dev.stekl0.materialproductivity.core.database.dao.ReminderDao
import dev.stekl0.materialproductivity.core.database.dao.TaskRepeatConfigDao
import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class TestNoteDao : NoteDao {
    private val e = MutableStateFlow(emptyList<NoteEntity>())

    override fun getNoteEntities(): Flow<List<NoteEntity>> = e

    override suspend fun getOneOffNoteEntities(): List<NoteEntity> = e.value

    override fun getNoteEntity(noteId: String): Flow<NoteEntity?> = e.map { notes -> notes.firstWithId(noteId) }

    override fun getNoteEntities(ids: List<String>): Flow<List<NoteEntity>> = e.map { notes -> notes.withIds(ids) }

    override fun getNoteEntitiesByProjectId(projectId: String): Flow<List<NoteEntity>> =
        e.map { notes -> notes.filter { it.projectId == projectId } }

    override fun getInboxNoteEntities(): Flow<List<NoteEntity>> = e.map(List<NoteEntity>::withoutProject)

    override fun getPinnedToTodayNoteEntities(): Flow<List<NoteEntity>> = e.map(List<NoteEntity>::pinned)

    override suspend fun upsertNotes(noteEntities: List<NoteEntity>) {
        e.update { oldValues -> (noteEntities + oldValues).distinctBy(NoteEntity::id) }
    }

    override suspend fun deleteNotes(ids: List<String>) {
        e.update { notes -> notes.filterNot { it.id in ids } }
    }
}

internal class TestReminderDao : ReminderDao {
    private val entities = MutableStateFlow(emptyList<ReminderEntity>())

    override fun getReminderEntities(): Flow<List<ReminderEntity>> = entities

    override suspend fun getOneOffReminderEntities(): List<ReminderEntity> = entities.value

    override fun getReminderEntity(reminderId: String): Flow<ReminderEntity?> =
        entities.map { reminders -> reminders.firstOrNull { it.id == reminderId } }

    override fun getReminderEntitiesByRelatedId(relatedId: String): Flow<List<ReminderEntity>> =
        entities.map { reminders -> reminders.filter { it.relatedId == relatedId } }

    override fun getDueReminderEntities(beforeOrAt: Long): Flow<List<ReminderEntity>> =
        entities.map { reminders -> reminders.filter { it.remindAt <= beforeOrAt } }

    override suspend fun upsertReminders(reminderEntities: List<ReminderEntity>) {
        entities.update { oldValues -> (reminderEntities + oldValues).distinctBy(ReminderEntity::id) }
    }

    override suspend fun deleteReminders(ids: List<String>) {
        entities.update { reminders -> reminders.filterNot { it.id in ids } }
    }
}

internal class TestTaskRepeatConfigDao : TaskRepeatConfigDao {
    private val entities = MutableStateFlow(emptyList<TaskRepeatConfigEntity>())

    override fun getTaskRepeatConfigEntities(): Flow<List<TaskRepeatConfigEntity>> = entities

    override suspend fun getOneOffTaskRepeatConfigEntities(): List<TaskRepeatConfigEntity> = entities.value

    override fun getTaskRepeatConfigEntity(repeatConfigId: String): Flow<TaskRepeatConfigEntity?> =
        entities.map { configs -> configs.firstOrNull { it.id == repeatConfigId } }

    override fun getTaskRepeatConfigEntities(ids: List<String>): Flow<List<TaskRepeatConfigEntity>> =
        entities.map { configs -> configs.filter { it.id in ids } }

    override fun getTaskRepeatConfigEntitiesByProjectId(projectId: String): Flow<List<TaskRepeatConfigEntity>> =
        entities.map { configs -> configs.filter { it.projectId == projectId } }

    override fun getActiveTaskRepeatConfigEntities(): Flow<List<TaskRepeatConfigEntity>> =
        entities.map { configs -> configs.filterNot(TaskRepeatConfigEntity::isPaused) }

    override suspend fun upsertTaskRepeatConfigs(repeatConfigEntities: List<TaskRepeatConfigEntity>) {
        entities.update { oldValues ->
            (repeatConfigEntities + oldValues).distinctBy(TaskRepeatConfigEntity::id)
        }
    }

    override suspend fun deleteTaskRepeatConfigs(ids: List<String>) {
        entities.update { configs -> configs.filterNot { it.id in ids } }
    }
}

internal class TestProjectFolderDao : ProjectFolderDao {
    private val entities = MutableStateFlow(emptyList<ProjectFolderEntity>())

    override fun getProjectFolderEntities(): Flow<List<ProjectFolderEntity>> = entities

    override suspend fun getOneOffProjectFolderEntities(): List<ProjectFolderEntity> = entities.value

    override fun getProjectFolderEntity(folderId: String): Flow<ProjectFolderEntity?> =
        entities.map { folders -> folders.firstOrNull { it.id == folderId } }

    override suspend fun upsertProjectFolders(folderEntities: List<ProjectFolderEntity>) {
        entities.update { oldValues -> (folderEntities + oldValues).distinctBy(ProjectFolderEntity::id) }
    }

    override suspend fun deleteProjectFolders(ids: List<String>) {
        entities.update { folders -> folders.filterNot { it.id in ids } }
    }
}

private fun List<NoteEntity>.firstWithId(id: String): NoteEntity? = firstOrNull { it.id == id }

private fun List<NoteEntity>.withIds(ids: List<String>): List<NoteEntity> = filter { it.id in ids }

private fun List<NoteEntity>.withoutProject(): List<NoteEntity> = filter { it.projectId == null }

private fun List<NoteEntity>.pinned(): List<NoteEntity> = filter(NoteEntity::isPinnedToToday)
