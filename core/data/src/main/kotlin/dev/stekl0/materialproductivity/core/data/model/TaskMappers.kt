package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import dev.stekl0.materialproductivity.core.model.HideSubTasksMode
import dev.stekl0.materialproductivity.core.model.Task
import dev.stekl0.materialproductivity.core.model.TaskAttachment
import dev.stekl0.materialproductivity.core.model.TaskAttachmentType
import dev.stekl0.materialproductivity.core.model.TaskIssue
import kotlinx.serialization.Serializable

internal fun TaskEntity.asExternalModel(): Task =
    Task(
        id = id,
        title = title,
        projectId = projectId,
        created = created,
        modified = modified,
        notes = notes,
        timeEstimate = timeEstimate,
        timeSpent = timeSpent,
        timeSpentOnDay = decodeLongMap(timeSpentOnDayJson),
        isDone = isDone,
        doneOn = doneOn,
        tagIds = decodeStringList(tagIdsJson),
        parentId = parentId,
        subTaskIds = decodeStringList(subTaskIdsJson),
        attachments = decodeAttachments(attachmentsJson),
        remindAt = remindAt,
        reminderId = reminderId,
        dueDay = dueDay,
        dueWithTime = dueWithTime,
        hasPlannedTime = hasPlannedTime,
        deadlineDay = deadlineDay,
        deadlineWithTime = deadlineWithTime,
        deadlineRemindAt = deadlineRemindAt,
        repeatCfgId = repeatCfgId,
        hideSubTasksMode = hideSubTasksMode?.let(::hideSubTasksMode),
        issue = issueJson?.let(::decodeTaskIssue),
    )

internal fun Task.asEntity(): TaskEntity =
    TaskEntity(
        id = id,
        title = title,
        projectId = projectId,
        created = created,
        modified = modified,
        notes = notes,
        timeEstimate = timeEstimate,
        timeSpent = timeSpent,
        timeSpentOnDayJson = encodeLongMap(timeSpentOnDay),
        isDone = isDone,
        doneOn = doneOn,
        tagIdsJson = encodeStringList(tagIds),
        parentId = parentId,
        subTaskIdsJson = encodeStringList(subTaskIds),
        attachmentsJson = encodeAttachments(attachments),
        remindAt = remindAt,
        reminderId = reminderId,
        dueDay = dueDay,
        dueWithTime = dueWithTime,
        hasPlannedTime = hasPlannedTime,
        deadlineDay = deadlineDay,
        deadlineWithTime = deadlineWithTime,
        deadlineRemindAt = deadlineRemindAt,
        repeatCfgId = repeatCfgId,
        hideSubTasksMode = hideSubTasksMode?.value,
        issueJson = issue?.let(::encodeTaskIssue),
    )

private fun hideSubTasksMode(value: Int): HideSubTasksMode? =
    decodeWireValue(
        value = value,
        values = HideSubTasksMode.entries,
        valueSelector = HideSubTasksMode::value,
        typeName = "HideSubTasksMode",
    )

private fun decodeTaskIssue(json: String): TaskIssue =
    DatabaseJson
        .decodeFromString<TaskIssueJson>(json)
        .asExternalModel()

private fun encodeTaskIssue(issue: TaskIssue): String = DatabaseJson.encodeToString(TaskIssueJson.from(issue))

private fun decodeAttachments(json: String): List<TaskAttachment> =
    DatabaseJson
        .decodeFromString<List<TaskAttachmentJson>>(json)
        .map(TaskAttachmentJson::asExternalModel)

private fun encodeAttachments(attachments: List<TaskAttachment>): String =
    DatabaseJson.encodeToString(attachments.map(TaskAttachmentJson::fromExternalModel))

@Serializable
private data class TaskIssueJson(
    val issueId: String? = null,
    val issueProviderId: String? = null,
    val issueType: String? = null,
    val issueWasUpdated: Boolean? = null,
    val issueLastUpdated: Long? = null,
    val issueAttachmentNr: Int? = null,
    val issueTimeTracked: Map<String, Long> = emptyMap(),
    val issuePoints: Int? = null,
) {
    fun asExternalModel(): TaskIssue =
        TaskIssue(
            issueId = issueId,
            issueProviderId = issueProviderId,
            issueType = issueType,
            issueWasUpdated = issueWasUpdated,
            issueLastUpdated = issueLastUpdated,
            issueAttachmentNr = issueAttachmentNr,
            issueTimeTracked = issueTimeTracked,
            issuePoints = issuePoints,
        )

    companion object {
        fun from(issue: TaskIssue): TaskIssueJson =
            TaskIssueJson(
                issueId = issue.issueId,
                issueProviderId = issue.issueProviderId,
                issueType = issue.issueType,
                issueWasUpdated = issue.issueWasUpdated,
                issueLastUpdated = issue.issueLastUpdated,
                issueAttachmentNr = issue.issueAttachmentNr,
                issueTimeTracked = issue.issueTimeTracked,
                issuePoints = issue.issuePoints,
            )
    }
}

@Serializable
private data class TaskAttachmentJson(
    val id: String? = null,
    val type: String,
    val title: String? = null,
    val path: String? = null,
    val icon: String? = null,
    val originalImgPath: String? = null,
) {
    fun asExternalModel(): TaskAttachment =
        TaskAttachment(
            id = id,
            type = decodeEnumName(type),
            title = title,
            path = path,
            icon = icon,
            originalImgPath = originalImgPath,
        )

    companion object {
        fun fromExternalModel(attachment: TaskAttachment): TaskAttachmentJson =
            TaskAttachmentJson(
                id = attachment.id,
                type = attachment.type.name,
                title = attachment.title,
                path = attachment.path,
                icon = attachment.icon,
                originalImgPath = attachment.originalImgPath,
            )
    }
}
