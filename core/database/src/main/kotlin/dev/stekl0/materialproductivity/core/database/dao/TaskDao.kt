package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface TaskDao {
    @Query(value = "SELECT * FROM tasks")
    public fun getTaskEntities(): Flow<List<TaskEntity>>

    @Query(value = "SELECT * FROM tasks")
    public suspend fun getOneOffTaskEntities(): List<TaskEntity>

    @Query(
        value = """
            SELECT * FROM tasks
            WHERE id = :taskId
        """,
    )
    public fun getTaskEntity(taskId: String): Flow<TaskEntity?>

    @Query(
        value = """
            SELECT * FROM tasks
            WHERE id IN (:ids)
        """,
    )
    public fun getTaskEntities(ids: List<String>): Flow<List<TaskEntity>>

    @Query(
        value = """
            SELECT * FROM tasks
            WHERE project_id = :projectId
        """,
    )
    public fun getTaskEntitiesByProjectId(projectId: String): Flow<List<TaskEntity>>

    @Query(
        value = """
            SELECT * FROM tasks
            WHERE project_id IS NULL
        """,
    )
    public fun getInboxTaskEntities(): Flow<List<TaskEntity>>

    @Query(
        value = """
            SELECT * FROM tasks
            WHERE parent_id = :parentId
        """,
    )
    public fun getSubTaskEntities(parentId: String): Flow<List<TaskEntity>>

    @Upsert
    public suspend fun upsertTasks(taskEntities: List<TaskEntity>)

    @Query(
        value = """
            DELETE FROM tasks
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteTasks(ids: List<String>)
}
