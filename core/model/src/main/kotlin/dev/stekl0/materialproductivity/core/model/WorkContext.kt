package dev.stekl0.materialproductivity.core.model

public data class WorkContext(
    public val id: String,
    public val title: String,
    public val type: WorkContextType,
    public val taskIds: List<String> = emptyList(),
    public val noteIds: List<String> = emptyList(),
    public val backlogTaskIds: List<String> = emptyList(),
    public val icon: String? = null,
    public val isEnableBacklog: Boolean = false,
    public val advancedConfig: WorkContextAdvancedConfig = WorkContextAdvancedConfig(),
    public val theme: WorkContextThemeConfig = WorkContextThemeConfig(),
)

public enum class WorkContextType {
    PROJECT,
    TAG,
}

public data class WorkContextAdvancedConfig(
    public val worklogExportSettings: WorklogExportSettings = WorklogExportSettings(),
)

public data class WorkContextThemeConfig(
    public val isAutoContrast: Boolean? = null,
    public val isDisableBackgroundTint: Boolean? = null,
    public val primary: String? = null,
    public val huePrimary: String? = null,
    public val accent: String? = null,
    public val hueAccent: String? = null,
    public val warn: String? = null,
    public val hueWarn: String? = null,
    public val backgroundImageDark: String? = null,
    public val backgroundImageLight: String? = null,
    public val backgroundOverlayOpacity: Float? = null,
)

public data class WorklogExportSettings(
    public val roundWorkTimeTo: RoundTimeOption? = null,
    public val roundStartTimeTo: RoundTimeOption? = null,
    public val roundEndTimeTo: RoundTimeOption? = null,
    public val separateTasksBy: String = DefaultTaskSeparator,
    public val cols: List<WorklogColumn> = emptyList(),
    public val groupBy: WorklogGrouping = WorklogGrouping.DATE,
)

public enum class RoundTimeOption(
    public val value: String,
) {
    MINUTES_5("5M"),
    QUARTER("QUARTER"),
    HALF("HALF"),
    HOUR("HOUR"),
}

public enum class WorklogColumn {
    EMPTY,
    DATE,
    START,
    END,
    TITLES,
    TITLES_INCLUDING_SUB,
    NOTES,
    PROJECTS,
    TAGS,
    TIME_MS,
    TIME_STR,
    TIME_CLOCK,
    ESTIMATE_MS,
    ESTIMATE_STR,
    ESTIMATE_CLOCK,
}

public enum class WorklogGrouping {
    DATE,
    PARENT,
    TASK,
    WORKLOG,
}

public const val DefaultTaskSeparator: String = ", "
