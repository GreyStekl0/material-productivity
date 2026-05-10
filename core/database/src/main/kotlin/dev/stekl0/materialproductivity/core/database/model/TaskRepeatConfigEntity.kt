package dev.stekl0.materialproductivity.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_repeat_configs")
public data class TaskRepeatConfigEntity(
    @PrimaryKey
    public val id: String,
    @ColumnInfo(name = "project_id")
    public val projectId: String?,
    public val title: String?,
    @ColumnInfo(name = "tag_ids_json")
    public val tagIdsJson: String,
    @ColumnInfo(name = "is_paused")
    public val isPaused: Boolean,
    @ColumnInfo(name = "quick_setting")
    public val quickSetting: String,
    @ColumnInfo(name = "repeat_cycle")
    public val repeatCycle: String,
    @ColumnInfo(name = "repeat_every")
    public val repeatEvery: Int,
    @ColumnInfo(name = "order_value")
    public val order: Int,
    @ColumnInfo(name = "default_estimate")
    public val defaultEstimate: Long?,
    @ColumnInfo(name = "start_date")
    public val startDate: String?,
    @ColumnInfo(name = "start_time")
    public val startTime: String?,
    @ColumnInfo(name = "remind_at")
    public val remindAt: String?,
    @ColumnInfo(name = "last_task_creation")
    public val lastTaskCreation: Long?,
    @ColumnInfo(name = "last_task_creation_day")
    public val lastTaskCreationDay: String?,
    public val monday: Boolean?,
    public val tuesday: Boolean?,
    public val wednesday: Boolean?,
    public val thursday: Boolean?,
    public val friday: Boolean?,
    public val saturday: Boolean?,
    public val sunday: Boolean?,
    @ColumnInfo(name = "monthly_week_of_month")
    public val monthlyWeekOfMonth: Int?,
    @ColumnInfo(name = "monthly_weekday")
    public val monthlyWeekday: Int?,
    public val notes: String?,
    @ColumnInfo(name = "should_inherit_subtasks")
    public val shouldInheritSubtasks: Boolean,
    @ColumnInfo(name = "repeat_from_completion_date")
    public val repeatFromCompletionDate: Boolean,
    @ColumnInfo(name = "disable_auto_update_subtasks")
    public val disableAutoUpdateSubtasks: Boolean,
    @ColumnInfo(name = "sub_task_templates_json")
    public val subTaskTemplatesJson: String,
    @ColumnInfo(name = "deleted_instance_dates_json")
    public val deletedInstanceDatesJson: String,
    @ColumnInfo(name = "skip_overdue")
    public val skipOverdue: Boolean,
)
