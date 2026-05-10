package dev.stekl0.materialproductivity.core.data.testdoubles

import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.database.dao.TaskDao
import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class TestTaskDao : TaskDao {
    private val entities = MutableStateFlow(emptyList<TaskEntity>())

    override fun getTaskEntities(): Flow<List<TaskEntity>> = entities

    override suspend fun getOneOffTaskEntities(): List<TaskEntity> = entities.value

    override fun getTaskEntity(taskId: String): Flow<TaskEntity?> = entities.map { tasks -> tasks.firstWithId(taskId) }

    override fun getTaskEntities(ids: List<String>): Flow<List<TaskEntity>> = entities.map { it.tasksWithIds(ids) }

    override fun getTaskEntitiesByProjectId(projectId: String): Flow<List<TaskEntity>> =
        entities.map { tasks -> tasks.filter { it.projectId == projectId } }

    override fun getInboxTaskEntities(): Flow<List<TaskEntity>> = entities.map(List<TaskEntity>::withoutProject)

    override fun getSubTaskEntities(parentId: String): Flow<List<TaskEntity>> =
        entities.map { tasks -> tasks.filter { it.parentId == parentId } }

    override suspend fun upsertTasks(taskEntities: List<TaskEntity>) {
        entities.update { oldValues -> (taskEntities + oldValues).distinctBy(TaskEntity::id) }
    }

    override suspend fun deleteTasks(ids: List<String>) {
        entities.update { tasks -> tasks.filterNot { it.id in ids } }
    }
}

internal class TestProjectDao : ProjectDao {
    private val entities = MutableStateFlow(emptyList<ProjectEntity>())

    override fun getProjectEntities(): Flow<List<ProjectEntity>> = entities

    override suspend fun getOneOffProjectEntities(): List<ProjectEntity> = entities.value

    override fun getProjectEntity(projectId: String): Flow<ProjectEntity?> =
        entities.map { projects -> projects.firstOrNull { it.id == projectId } }

    override fun getProjectEntities(ids: List<String>): Flow<List<ProjectEntity>> =
        entities.map { projects -> projects.filter { it.id in ids } }

    override suspend fun upsertProjects(projectEntities: List<ProjectEntity>) {
        entities.update { oldValues -> (projectEntities + oldValues).distinctBy(ProjectEntity::id) }
    }

    override suspend fun deleteProjects(ids: List<String>) {
        entities.update { projects -> projects.filterNot { it.id in ids } }
    }
}

internal class TestTagDao : TagDao {
    private val entities = MutableStateFlow(emptyList<TagEntity>())

    override fun getTagEntities(): Flow<List<TagEntity>> = entities

    override suspend fun getOneOffTagEntities(): List<TagEntity> = entities.value

    override fun getTagEntity(tagId: String): Flow<TagEntity?> = entities.map { tags -> tags.firstWithId(tagId) }

    override fun getTagEntities(ids: List<String>): Flow<List<TagEntity>> = entities.map { it.tagsWithIds(ids) }

    override suspend fun upsertTags(tagEntities: List<TagEntity>) {
        entities.update { oldValues -> (tagEntities + oldValues).distinctBy(TagEntity::id) }
    }

    override suspend fun deleteTags(ids: List<String>) {
        entities.update { tags -> tags.filterNot { it.id in ids } }
    }
}

private fun List<TaskEntity>.firstWithId(id: String): TaskEntity? = firstOrNull { it.id == id }

private fun List<TagEntity>.firstWithId(id: String): TagEntity? = firstOrNull { it.id == id }

private fun List<TaskEntity>.tasksWithIds(ids: List<String>): List<TaskEntity> = filter { it.id in ids }

private fun List<TaskEntity>.withoutProject(): List<TaskEntity> = filter { it.projectId == null }

private fun List<TagEntity>.tagsWithIds(ids: List<String>): List<TagEntity> = filter { it.id in ids }
