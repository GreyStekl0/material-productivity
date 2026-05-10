package dev.stekl0.materialproductivity.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
public data class ReminderEntity(
    @PrimaryKey
    public val id: String,
    @ColumnInfo(name = "remind_at")
    public val remindAt: Long,
    public val title: String,
    public val type: String,
    @ColumnInfo(name = "related_id")
    public val relatedId: String,
    @ColumnInfo(name = "recurring_config_json")
    public val recurringConfigJson: String,
)
