package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.model.TaskRepeatConfig
import kotlinx.coroutines.flow.Flow

public interface TaskRepeatConfigsRepository {
    public fun getTaskRepeatConfigs(): Flow<List<TaskRepeatConfig>>

    public fun getTaskRepeatConfig(repeatConfigId: String): Flow<TaskRepeatConfig?>

    public fun getProjectTaskRepeatConfigs(projectId: String): Flow<List<TaskRepeatConfig>>

    public fun getActiveTaskRepeatConfigs(): Flow<List<TaskRepeatConfig>>

    public suspend fun upsertTaskRepeatConfigs(configs: List<TaskRepeatConfig>)

    public suspend fun deleteTaskRepeatConfigs(ids: List<String>)
}
