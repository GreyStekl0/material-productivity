package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.model.asEntity
import dev.stekl0.materialproductivity.core.data.model.asExternalModel
import dev.stekl0.materialproductivity.core.database.dao.TaskRepeatConfigDao
import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity
import dev.stekl0.materialproductivity.core.model.TaskRepeatConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class OfflineFirstTaskRepeatConfigsRepository(
    private val taskRepeatConfigDao: TaskRepeatConfigDao,
) : TaskRepeatConfigsRepository {
    override fun getTaskRepeatConfigs(): Flow<List<TaskRepeatConfig>> =
        taskRepeatConfigDao
            .getTaskRepeatConfigEntities()
            .map { entities -> entities.asExternalModelsOrderedByConfigOrder() }

    override fun getTaskRepeatConfig(repeatConfigId: String): Flow<TaskRepeatConfig?> =
        taskRepeatConfigDao
            .getTaskRepeatConfigEntity(repeatConfigId)
            .map { entity -> entity?.asExternalModel() }

    override fun getProjectTaskRepeatConfigs(projectId: String): Flow<List<TaskRepeatConfig>> =
        taskRepeatConfigDao
            .getTaskRepeatConfigEntitiesByProjectId(projectId)
            .map { entities -> entities.asExternalModelsOrderedByConfigOrder() }

    override fun getActiveTaskRepeatConfigs(): Flow<List<TaskRepeatConfig>> =
        taskRepeatConfigDao
            .getActiveTaskRepeatConfigEntities()
            .map { entities -> entities.asExternalModelsOrderedByConfigOrder() }

    override suspend fun upsertTaskRepeatConfigs(configs: List<TaskRepeatConfig>) {
        taskRepeatConfigDao.upsertTaskRepeatConfigs(configs.map(TaskRepeatConfig::asEntity))
    }

    override suspend fun deleteTaskRepeatConfigs(ids: List<String>) {
        taskRepeatConfigDao.deleteTaskRepeatConfigs(ids)
    }

    private fun List<TaskRepeatConfigEntity>.asExternalModelsOrderedByConfigOrder(): List<TaskRepeatConfig> =
        map(TaskRepeatConfigEntity::asExternalModel).sortedBy(TaskRepeatConfig::order)
}
