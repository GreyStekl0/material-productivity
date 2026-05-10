package dev.stekl0.materialproductivity.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
public data class TaskEntity(
    @PrimaryKey
    public val id: String,
    public val title: String,
    @ColumnInfo(name = "project_id")
    public val projectId: String?,
    public val created: Long,
    public val modified: Long?,
    public val notes: String?,
    @ColumnInfo(name = "time_estimate")
    public val timeEstimate: Long,
    @ColumnInfo(name = "time_spent")
    public val timeSpent: Long,
    @ColumnInfo(name = "time_spent_on_day_json")
    public val timeSpentOnDayJson: String,
    @ColumnInfo(name = "is_done")
    public val isDone: Boolean,
    @ColumnInfo(name = "done_on")
    public val doneOn: Long?,
    @ColumnInfo(name = "tag_ids_json")
    public val tagIdsJson: String,
    @ColumnInfo(name = "parent_id")
    public val parentId: String?,
    @ColumnInfo(name = "sub_task_ids_json")
    public val subTaskIdsJson: String,
    @ColumnInfo(name = "attachments_json")
    public val attachmentsJson: String,
    @ColumnInfo(name = "remind_at")
    public val remindAt: Long?,
    @ColumnInfo(name = "reminder_id")
    public val reminderId: String?,
    @ColumnInfo(name = "due_day")
    public val dueDay: String?,
    @ColumnInfo(name = "due_with_time")
    public val dueWithTime: Long?,
    @ColumnInfo(name = "has_planned_time")
    public val hasPlannedTime: Boolean,
    @ColumnInfo(name = "deadline_day")
    public val deadlineDay: String?,
    @ColumnInfo(name = "deadline_with_time")
    public val deadlineWithTime: Long?,
    @ColumnInfo(name = "deadline_remind_at")
    public val deadlineRemindAt: Long?,
    @ColumnInfo(name = "repeat_cfg_id")
    public val repeatCfgId: String?,
    @ColumnInfo(name = "hide_sub_tasks_mode")
    public val hideSubTasksMode: Int?,
    @ColumnInfo(name = "issue_json")
    public val issueJson: String?,
)
