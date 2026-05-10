package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface TaskRepeatConfigDao {
    @Query(value = "SELECT * FROM task_repeat_configs")
    public fun getTaskRepeatConfigEntities(): Flow<List<TaskRepeatConfigEntity>>

    @Query(value = "SELECT * FROM task_repeat_configs")
    public suspend fun getOneOffTaskRepeatConfigEntities(): List<TaskRepeatConfigEntity>

    @Query(
        value = """
            SELECT * FROM task_repeat_configs
            WHERE id = :repeatConfigId
        """,
    )
    public fun getTaskRepeatConfigEntity(repeatConfigId: String): Flow<TaskRepeatConfigEntity?>

    @Query(
        value = """
            SELECT * FROM task_repeat_configs
            WHERE id IN (:ids)
        """,
    )
    public fun getTaskRepeatConfigEntities(ids: List<String>): Flow<List<TaskRepeatConfigEntity>>

    @Query(
        value = """
            SELECT * FROM task_repeat_configs
            WHERE project_id = :projectId
        """,
    )
    public fun getTaskRepeatConfigEntitiesByProjectId(projectId: String): Flow<List<TaskRepeatConfigEntity>>

    @Query(
        value = """
            SELECT * FROM task_repeat_configs
            WHERE is_paused = 0
        """,
    )
    public fun getActiveTaskRepeatConfigEntities(): Flow<List<TaskRepeatConfigEntity>>

    @Upsert
    public suspend fun upsertTaskRepeatConfigs(repeatConfigEntities: List<TaskRepeatConfigEntity>)

    @Query(
        value = """
            DELETE FROM task_repeat_configs
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteTaskRepeatConfigs(ids: List<String>)
}
