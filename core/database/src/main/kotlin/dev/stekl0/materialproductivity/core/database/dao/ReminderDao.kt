package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface ReminderDao {
    @Query(value = "SELECT * FROM reminders")
    public fun getReminderEntities(): Flow<List<ReminderEntity>>

    @Query(value = "SELECT * FROM reminders")
    public suspend fun getOneOffReminderEntities(): List<ReminderEntity>

    @Query(
        value = """
            SELECT * FROM reminders
            WHERE id = :reminderId
        """,
    )
    public fun getReminderEntity(reminderId: String): Flow<ReminderEntity?>

    @Query(
        value = """
            SELECT * FROM reminders
            WHERE related_id = :relatedId
        """,
    )
    public fun getReminderEntitiesByRelatedId(relatedId: String): Flow<List<ReminderEntity>>

    @Query(
        value = """
            SELECT * FROM reminders
            WHERE remind_at <= :beforeOrAt
        """,
    )
    public fun getDueReminderEntities(beforeOrAt: Long): Flow<List<ReminderEntity>>

    @Upsert
    public suspend fun upsertReminders(reminderEntities: List<ReminderEntity>)

    @Query(
        value = """
            DELETE FROM reminders
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteReminders(ids: List<String>)
}
