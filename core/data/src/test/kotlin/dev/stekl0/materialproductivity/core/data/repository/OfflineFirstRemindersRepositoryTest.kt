package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.testdoubles.TestReminderDao
import dev.stekl0.materialproductivity.core.data.testdoubles.testReminderEntity
import dev.stekl0.materialproductivity.core.database.dao.ReminderDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfflineFirstRemindersRepositoryTest {
    private lateinit var subject: OfflineFirstRemindersRepository
    private lateinit var reminderDao: ReminderDao

    @BeforeEach
    fun setup() {
        reminderDao = TestReminderDao()
        subject =
            OfflineFirstRemindersRepository(
                reminderDao = reminderDao,
            )
    }

    @Test
    fun dueReminders_returnsOnlyRemindersBeforeOrAtThreshold() =
        runTest {
            reminderDao.upsertReminders(
                listOf(
                    testReminderEntity(id = "due", remindAt = 1L),
                    testReminderEntity(id = "future", remindAt = 3L),
                ),
            )

            assertEquals(
                listOf("due"),
                subject.getDueReminders(beforeOrAt = 2L).first().map { it.id },
            )
        }

    @Test
    fun remindersForRelatedId_returnsOnlyRelatedReminders() =
        runTest {
            reminderDao.upsertReminders(
                listOf(
                    testReminderEntity(id = "related", remindAt = 1L, relatedId = "task-id"),
                    testReminderEntity(id = "other", remindAt = 1L, relatedId = "other-task-id"),
                ),
            )

            assertEquals(
                listOf("related"),
                subject.getRemindersForRelatedId("task-id").first().map { it.id },
            )
        }
}
