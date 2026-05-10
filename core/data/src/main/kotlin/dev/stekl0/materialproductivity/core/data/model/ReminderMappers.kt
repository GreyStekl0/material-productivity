package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import dev.stekl0.materialproductivity.core.model.Reminder
import dev.stekl0.materialproductivity.core.model.ReminderType
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

internal fun ReminderEntity.asExternalModel(): Reminder =
    Reminder(
        id = id,
        remindAt = remindAt,
        title = title,
        type = decodeEnumName(type),
        relatedId = relatedId,
        recurringConfig = decodeRecurringConfig(recurringConfigJson),
    )

internal fun Reminder.asEntity(): ReminderEntity =
    ReminderEntity(
        id = id,
        remindAt = remindAt,
        title = title,
        type = type.name,
        relatedId = relatedId,
        recurringConfigJson = encodeRecurringConfig(recurringConfig),
    )

private fun decodeRecurringConfig(json: String): Map<String, String> =
    DatabaseJson.decodeFromString(MapSerializer(String.serializer(), String.serializer()), json)

private fun encodeRecurringConfig(config: Map<String, String>): String =
    DatabaseJson.encodeToString(MapSerializer(String.serializer(), String.serializer()), config)
