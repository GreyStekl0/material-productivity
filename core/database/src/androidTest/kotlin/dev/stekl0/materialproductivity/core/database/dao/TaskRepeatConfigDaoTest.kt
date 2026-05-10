package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class TaskRepeatConfigDaoTest : DatabaseTest() {
    @Test
    fun getTaskRepeatConfigEntity_readsInsertedTaskRepeatConfig() =
        runTest {
            val repeatConfig = testTaskRepeatConfigEntity()

            taskRepeatConfigDao.upsertTaskRepeatConfigs(listOf(repeatConfig))

            assertEquals(
                repeatConfig,
                taskRepeatConfigDao.getTaskRepeatConfigEntity(repeatConfig.id).first(),
            )
        }

    @Test
    fun getActiveTaskRepeatConfigEntities_returnsOnlyUnpausedTaskRepeatConfigs() =
        runTest {
            val activeConfig = testTaskRepeatConfigEntity(id = "active-config-id", isPaused = false)
            val pausedConfig = testTaskRepeatConfigEntity(id = "paused-config-id", isPaused = true)

            taskRepeatConfigDao.upsertTaskRepeatConfigs(listOf(activeConfig, pausedConfig))

            assertEquals(
                listOf(activeConfig),
                taskRepeatConfigDao.getActiveTaskRepeatConfigEntities().first(),
            )
        }
}
