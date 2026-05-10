package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.testdoubles.TestTaskRepeatConfigDao
import dev.stekl0.materialproductivity.core.data.testdoubles.testTaskRepeatConfigEntity
import dev.stekl0.materialproductivity.core.database.dao.TaskRepeatConfigDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfflineFirstTaskRepeatConfigsRepositoryTest {
    private lateinit var subject: OfflineFirstTaskRepeatConfigsRepository
    private lateinit var taskRepeatConfigDao: TaskRepeatConfigDao

    @BeforeEach
    fun setup() {
        taskRepeatConfigDao = TestTaskRepeatConfigDao()
        subject =
            OfflineFirstTaskRepeatConfigsRepository(
                taskRepeatConfigDao = taskRepeatConfigDao,
            )
    }

    @Test
    fun repeatConfigs_areOrderedByConfigOrder() =
        runTest {
            taskRepeatConfigDao.upsertTaskRepeatConfigs(
                listOf(
                    testTaskRepeatConfigEntity(id = "second", order = 2),
                    testTaskRepeatConfigEntity(id = "first", order = 1),
                ),
            )

            assertEquals(
                listOf("first", "second"),
                subject.getTaskRepeatConfigs().first().map { it.id },
            )
        }

    @Test
    fun activeRepeatConfigs_returnOnlyUnpausedConfigsOrderedByConfigOrder() =
        runTest {
            taskRepeatConfigDao.upsertTaskRepeatConfigs(
                listOf(
                    testTaskRepeatConfigEntity(id = "active-2", order = 2, isPaused = false),
                    testTaskRepeatConfigEntity(id = "paused", order = 1, isPaused = true),
                    testTaskRepeatConfigEntity(id = "active-1", order = 1, isPaused = false),
                ),
            )

            assertEquals(
                listOf("active-1", "active-2"),
                subject.getActiveTaskRepeatConfigs().first().map { it.id },
            )
        }
}
