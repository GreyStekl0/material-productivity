package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.model.asEntity
import dev.stekl0.materialproductivity.core.data.model.asExternalModel
import dev.stekl0.materialproductivity.core.database.dao.ReminderDao
import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import dev.stekl0.materialproductivity.core.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class OfflineFirstRemindersRepository(
    private val reminderDao: ReminderDao,
) : RemindersRepository {
    override fun getReminders(): Flow<List<Reminder>> =
        reminderDao
            .getReminderEntities()
            .map { entities -> entities.map(ReminderEntity::asExternalModel) }

    override fun getReminder(reminderId: String): Flow<Reminder?> =
        reminderDao
            .getReminderEntity(reminderId)
            .map { entity -> entity?.asExternalModel() }

    override fun getRemindersForRelatedId(relatedId: String): Flow<List<Reminder>> =
        reminderDao
            .getReminderEntitiesByRelatedId(relatedId)
            .map { entities -> entities.map(ReminderEntity::asExternalModel) }

    override fun getDueReminders(beforeOrAt: Long): Flow<List<Reminder>> =
        reminderDao
            .getDueReminderEntities(beforeOrAt)
            .map { entities -> entities.map(ReminderEntity::asExternalModel) }

    override suspend fun upsertReminders(reminders: List<Reminder>) {
        reminderDao.upsertReminders(reminders.map(Reminder::asEntity))
    }

    override suspend fun deleteReminders(ids: List<String>) {
        reminderDao.deleteReminders(ids)
    }
}
