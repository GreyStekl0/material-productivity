package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ReminderDaoTest : DatabaseTest() {
    @Test
    fun getReminderEntity_readsInsertedReminder() =
        runTest {
            val reminder = testReminderEntity()

            reminderDao.upsertReminders(listOf(reminder))

            assertEquals(reminder, reminderDao.getReminderEntity(reminder.id).first())
        }

    @Test
    fun getDueReminderEntities_returnsOnlyDueReminders() =
        runTest {
            val dueReminder = testReminderEntity(id = "due-reminder-id", remindAt = 1L)
            val futureReminder = testReminderEntity(id = "future-reminder-id", remindAt = 3L)

            reminderDao.upsertReminders(listOf(dueReminder, futureReminder))

            assertEquals(
                listOf(dueReminder),
                reminderDao.getDueReminderEntities(beforeOrAt = 2L).first(),
            )
        }
}
