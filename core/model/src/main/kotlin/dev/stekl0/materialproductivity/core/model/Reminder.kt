package dev.stekl0.materialproductivity.core.model

public data class Reminder(
    public val id: String,
    public val remindAt: Long,
    public val title: String,
    public val type: ReminderType,
    public val relatedId: String,
    public val recurringConfig: Map<String, String> = emptyMap(),
)

public enum class ReminderType {
    NOTE,
    TASK,
}
