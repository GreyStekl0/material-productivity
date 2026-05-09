package dev.stekl0.materialproductivity.core.model

public data class TaskRepeatConfig(
    public val id: String,
    public val projectId: String?,
    public val title: String?,
    public val tagIds: List<String> = emptyList(),
    public val isPaused: Boolean = false,
    public val quickSetting: RepeatQuickSetting = RepeatQuickSetting.DAILY,
    public val repeatCycle: RepeatCycle = RepeatCycle.WEEKLY,
    public val repeatEvery: Int = 1,
    public val order: Int = 0,
    public val defaultEstimate: Long? = null,
    public val startDate: String? = null,
    public val startTime: String? = null,
    public val remindAt: TaskReminderOption? = null,
    public val lastTaskCreation: Long? = null,
    public val lastTaskCreationDay: String? = null,
    public val monday: Boolean? = null,
    public val tuesday: Boolean? = null,
    public val wednesday: Boolean? = null,
    public val thursday: Boolean? = null,
    public val friday: Boolean? = null,
    public val saturday: Boolean? = null,
    public val sunday: Boolean? = null,
    public val monthlyWeekOfMonth: MonthlyWeekOfMonth? = null,
    public val monthlyWeekday: MonthlyWeekday? = null,
    public val notes: String? = null,
    public val shouldInheritSubtasks: Boolean = false,
    public val repeatFromCompletionDate: Boolean = false,
    public val disableAutoUpdateSubtasks: Boolean = false,
    public val subTaskTemplates: List<SubTaskTemplate> = emptyList(),
    public val deletedInstanceDates: List<String> = emptyList(),
    public val skipOverdue: Boolean = false,
)

public enum class RepeatCycle {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
}

public enum class RepeatQuickSetting {
    DAILY,
    WEEKLY_CURRENT_WEEKDAY,
    MONTHLY_CURRENT_DATE,
    MONTHLY_FIRST_DAY,
    MONTHLY_LAST_DAY,
    MONTHLY_NTH_WEEKDAY,
    MONDAY_TO_FRIDAY,
    YEARLY_CURRENT_DATE,
    CUSTOM,
}

public enum class TaskReminderOption(
    public val value: String,
) {
    DO_NOT_REMIND("DoNotRemind"),
    AT_START("AtStart"),
    M5("m5"),
    M10("m10"),
    M15("m15"),
    M30("m30"),
    H1("h1"),
}

@Suppress("MagicNumber")
public enum class MonthlyWeekOfMonth(
    public val value: Int,
) {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    LAST(-1),
}

@Suppress("MagicNumber")
public enum class MonthlyWeekday(
    public val value: Int,
) {
    SUNDAY(0),
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
}

public data class SubTaskTemplate(
    public val title: String,
    public val timeEstimate: Long? = null,
    public val notes: String? = null,
)
