package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity
import dev.stekl0.materialproductivity.core.model.MonthlyWeekOfMonth
import dev.stekl0.materialproductivity.core.model.MonthlyWeekday
import dev.stekl0.materialproductivity.core.model.RepeatCycle
import dev.stekl0.materialproductivity.core.model.RepeatQuickSetting
import dev.stekl0.materialproductivity.core.model.SubTaskTemplate
import dev.stekl0.materialproductivity.core.model.TaskReminderOption
import dev.stekl0.materialproductivity.core.model.TaskRepeatConfig
import kotlinx.serialization.Serializable

internal fun TaskRepeatConfigEntity.asExternalModel(): TaskRepeatConfig =
    TaskRepeatConfig(
        id = id,
        projectId = projectId,
        title = title,
        tagIds = decodeStringList(tagIdsJson),
        isPaused = isPaused,
        quickSetting = decodeEnumName(quickSetting),
        repeatCycle = decodeEnumName(repeatCycle),
        repeatEvery = repeatEvery,
        order = order,
        defaultEstimate = defaultEstimate,
        startDate = startDate,
        startTime = startTime,
        remindAt = remindAt?.let(::reminder),
        lastTaskCreation = lastTaskCreation,
        lastTaskCreationDay = lastTaskCreationDay,
        monday = monday,
        tuesday = tuesday,
        wednesday = wednesday,
        thursday = thursday,
        friday = friday,
        saturday = saturday,
        sunday = sunday,
        monthlyWeekOfMonth = monthlyWeekOfMonth?.let(::weekOfMonth),
        monthlyWeekday = monthlyWeekday?.let(::weekday),
        notes = notes,
        shouldInheritSubtasks = shouldInheritSubtasks,
        repeatFromCompletionDate = repeatFromCompletionDate,
        disableAutoUpdateSubtasks = disableAutoUpdateSubtasks,
        subTaskTemplates = decodeSubTaskTemplates(subTaskTemplatesJson),
        deletedInstanceDates = decodeStringList(deletedInstanceDatesJson),
        skipOverdue = skipOverdue,
    )

internal fun TaskRepeatConfig.asEntity(): TaskRepeatConfigEntity =
    TaskRepeatConfigEntity(
        id = id,
        projectId = projectId,
        title = title,
        tagIdsJson = encodeStringList(tagIds),
        isPaused = isPaused,
        quickSetting = quickSetting.name,
        repeatCycle = repeatCycle.name,
        repeatEvery = repeatEvery,
        order = order,
        defaultEstimate = defaultEstimate,
        startDate = startDate,
        startTime = startTime,
        remindAt = remindAt?.value,
        lastTaskCreation = lastTaskCreation,
        lastTaskCreationDay = lastTaskCreationDay,
        monday = monday,
        tuesday = tuesday,
        wednesday = wednesday,
        thursday = thursday,
        friday = friday,
        saturday = saturday,
        sunday = sunday,
        monthlyWeekOfMonth = monthlyWeekOfMonth?.value,
        monthlyWeekday = monthlyWeekday?.value,
        notes = notes,
        shouldInheritSubtasks = shouldInheritSubtasks,
        repeatFromCompletionDate = repeatFromCompletionDate,
        disableAutoUpdateSubtasks = disableAutoUpdateSubtasks,
        subTaskTemplatesJson = encodeSubTaskTemplates(subTaskTemplates),
        deletedInstanceDatesJson = encodeStringList(deletedInstanceDates),
        skipOverdue = skipOverdue,
    )

private fun reminder(value: String): TaskReminderOption? =
    decodeWireValue(
        value = value,
        values = TaskReminderOption.entries,
        valueSelector = TaskReminderOption::value,
        typeName = "TaskReminderOption",
    )

private fun weekOfMonth(value: Int): MonthlyWeekOfMonth? =
    decodeWireValue(
        value = value,
        values = MonthlyWeekOfMonth.entries,
        valueSelector = MonthlyWeekOfMonth::value,
        typeName = "MonthlyWeekOfMonth",
    )

private fun weekday(value: Int): MonthlyWeekday? =
    decodeWireValue(
        value = value,
        values = MonthlyWeekday.entries,
        valueSelector = MonthlyWeekday::value,
        typeName = "MonthlyWeekday",
    )

private fun decodeSubTaskTemplates(json: String): List<SubTaskTemplate> =
    DatabaseJson
        .decodeFromString<List<SubTaskTemplateJson>>(json)
        .map(SubTaskTemplateJson::asExternalModel)

private fun encodeSubTaskTemplates(templates: List<SubTaskTemplate>): String =
    DatabaseJson.encodeToString(templates.map(SubTaskTemplateJson::fromExternalModel))

@Serializable
private data class SubTaskTemplateJson(
    val title: String,
    val timeEstimate: Long? = null,
    val notes: String? = null,
) {
    fun asExternalModel(): SubTaskTemplate =
        SubTaskTemplate(
            title = title,
            timeEstimate = timeEstimate,
            notes = notes,
        )

    companion object {
        fun fromExternalModel(template: SubTaskTemplate): SubTaskTemplateJson =
            SubTaskTemplateJson(
                title = template.title,
                timeEstimate = template.timeEstimate,
                notes = template.notes,
            )
    }
}
