package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.model.asEntity
import dev.stekl0.materialproductivity.core.data.model.asExternalModel
import dev.stekl0.materialproductivity.core.data.model.orderedByIds
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.database.dao.TaskDao
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import dev.stekl0.materialproductivity.core.model.Project
import dev.stekl0.materialproductivity.core.model.Tag
import dev.stekl0.materialproductivity.core.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

internal class OfflineFirstTasksRepository(
    private val taskDao: TaskDao,
    private val projectDao: ProjectDao,
    private val tagDao: TagDao,
) : TasksRepository {
    override fun getTasks(): Flow<List<Task>> =
        taskDao
            .getTaskEntities()
            .map { entities -> entities.map(TaskEntity::asExternalModel) }

    override fun getTask(taskId: String): Flow<Task?> =
        taskDao
            .getTaskEntity(taskId)
            .map { entity -> entity?.asExternalModel() }

    override fun getInboxTasks(): Flow<List<Task>> =
        taskDao
            .getInboxTaskEntities()
            .map { entities -> entities.map(TaskEntity::asExternalModel) }

    override fun getProjectTasks(projectId: String): Flow<List<Task>> =
        combine(
            projectDao.getProjectEntity(projectId).map { entity -> entity?.asExternalModel() },
            taskDao.getTaskEntities(),
        ) { project, tasks ->
            tasks.orderedByProjectIds(project, Project::taskIds)
        }

    override fun getProjectBacklogTasks(projectId: String): Flow<List<Task>> =
        combine(
            projectDao.getProjectEntity(projectId).map { entity -> entity?.asExternalModel() },
            taskDao.getTaskEntities(),
        ) { project, tasks ->
            tasks.orderedByProjectIds(project, Project::backlogTaskIds)
        }

    override fun getTagTasks(tagId: String): Flow<List<Task>> =
        combine(
            tagDao.getTagEntity(tagId).map { entity -> entity?.asExternalModel() },
            taskDao.getTaskEntities(),
        ) { tag, tasks ->
            tasks.orderedByTagIds(tag)
        }

    override fun getSubTasks(parentTaskId: String): Flow<List<Task>> =
        combine(
            taskDao.getTaskEntity(parentTaskId).map { entity -> entity?.asExternalModel() },
            taskDao.getTaskEntities(),
        ) { parentTask, tasks ->
            tasks
                .map(TaskEntity::asExternalModel)
                .orderedByIds(parentTask?.subTaskIds.orEmpty(), Task::id)
        }

    override suspend fun upsertTasks(tasks: List<Task>) {
        taskDao.upsertTasks(tasks.map(Task::asEntity))
    }

    override suspend fun deleteTasks(ids: List<String>) {
        taskDao.deleteTasks(ids)
    }
}

private fun List<TaskEntity>.orderedByProjectIds(
    project: Project?,
    idSelector: (Project) -> List<String>,
): List<Task> =
    map(TaskEntity::asExternalModel)
        .orderedByIds(project?.let(idSelector).orEmpty(), Task::id)

private fun List<TaskEntity>.orderedByTagIds(tag: Tag?): List<Task> =
    map(TaskEntity::asExternalModel)
        .orderedByIds(tag?.taskIds.orEmpty(), Task::id)
