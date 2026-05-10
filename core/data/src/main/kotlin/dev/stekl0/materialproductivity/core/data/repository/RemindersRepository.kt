package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.model.Reminder
import kotlinx.coroutines.flow.Flow

public interface RemindersRepository {
    public fun getReminders(): Flow<List<Reminder>>

    public fun getReminder(reminderId: String): Flow<Reminder?>

    public fun getRemindersForRelatedId(relatedId: String): Flow<List<Reminder>>

    public fun getDueReminders(beforeOrAt: Long): Flow<List<Reminder>>

    public suspend fun upsertReminders(reminders: List<Reminder>)

    public suspend fun deleteReminders(ids: List<String>)
}
