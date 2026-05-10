package dev.stekl0.materialproductivity.core.model

public data class Task(
    public val id: String,
    public val title: String,
    public val projectId: String?,
    public val created: Long,
    public val modified: Long? = null,
    public val notes: String? = null,
    public val timeEstimate: Long = 0,
    public val timeSpent: Long = 0,
    public val timeSpentOnDay: Map<String, Long> = emptyMap(),
    public val isDone: Boolean = false,
    public val doneOn: Long? = null,
    public val tagIds: List<String> = emptyList(),
    public val parentId: String? = null,
    public val subTaskIds: List<String> = emptyList(),
    public val attachments: List<TaskAttachment> = emptyList(),
    public val remindAt: Long? = null,
    public val reminderId: String? = null,
    public val dueDay: String? = null,
    public val dueWithTime: Long? = null,
    public val hasPlannedTime: Boolean = false,
    public val deadlineDay: String? = null,
    public val deadlineWithTime: Long? = null,
    public val deadlineRemindAt: Long? = null,
    public val repeatCfgId: String? = null,
    public val hideSubTasksMode: HideSubTasksMode? = null,
    public val issue: TaskIssue? = null,
)

@Suppress("MagicNumber")
public enum class HideSubTasksMode(
    public val value: Int,
) {
    HIDE_DONE(1),
    HIDE_ALL(2),
}

public data class TaskIssue(
    public val issueId: String? = null,
    public val issueProviderId: String? = null,
    public val issueType: String? = null,
    public val issueWasUpdated: Boolean? = null,
    public val issueLastUpdated: Long? = null,
    public val issueAttachmentNr: Int? = null,
    public val issueTimeTracked: Map<String, Long> = emptyMap(),
    public val issuePoints: Int? = null,
)

public data class TaskAttachment(
    public val id: String?,
    public val type: TaskAttachmentType,
    public val title: String? = null,
    public val path: String? = null,
    public val icon: String? = null,
    public val originalImgPath: String? = null,
)

public enum class TaskAttachmentType {
    FILE,
    LINK,
    IMG,
    COMMAND,
    NOTE,
}
