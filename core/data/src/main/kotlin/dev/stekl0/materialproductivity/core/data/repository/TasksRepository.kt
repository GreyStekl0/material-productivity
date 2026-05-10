package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.model.Task
import kotlinx.coroutines.flow.Flow

public interface TasksRepository {
    public fun getTasks(): Flow<List<Task>>

    public fun getTask(taskId: String): Flow<Task?>

    public fun getInboxTasks(): Flow<List<Task>>

    public fun getProjectTasks(projectId: String): Flow<List<Task>>

    public fun getProjectBacklogTasks(projectId: String): Flow<List<Task>>

    public fun getTagTasks(tagId: String): Flow<List<Task>>

    public fun getSubTasks(parentTaskId: String): Flow<List<Task>>

    public suspend fun upsertTasks(tasks: List<Task>)

    public suspend fun deleteTasks(ids: List<String>)
}
