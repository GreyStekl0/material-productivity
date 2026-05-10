package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.model.DefaultTaskSeparator
import dev.stekl0.materialproductivity.core.model.RoundTimeOption
import dev.stekl0.materialproductivity.core.model.WorkContextAdvancedConfig
import dev.stekl0.materialproductivity.core.model.WorkContextThemeConfig
import dev.stekl0.materialproductivity.core.model.WorklogColumn
import dev.stekl0.materialproductivity.core.model.WorklogExportSettings
import dev.stekl0.materialproductivity.core.model.WorklogGrouping
import kotlinx.serialization.Serializable

internal fun decodeAdvancedConfig(json: String): WorkContextAdvancedConfig =
    DatabaseJson
        .decodeFromString<WorkContextAdvancedConfigJson>(json)
        .asExternalModel()

internal fun encodeAdvancedConfig(config: WorkContextAdvancedConfig): String =
    DatabaseJson.encodeToString(WorkContextAdvancedConfigJson.fromExternalModel(config))

internal fun decodeThemeConfig(json: String): WorkContextThemeConfig =
    DatabaseJson
        .decodeFromString<WorkContextThemeConfigJson>(json)
        .asExternalModel()

internal fun encodeThemeConfig(config: WorkContextThemeConfig): String =
    DatabaseJson.encodeToString(WorkContextThemeConfigJson.fromExternalModel(config))

@Serializable
private data class WorkContextAdvancedConfigJson(
    val worklogExportSettings: WorklogExportSettingsJson = WorklogExportSettingsJson(),
) {
    fun asExternalModel(): WorkContextAdvancedConfig =
        WorkContextAdvancedConfig(
            worklogExportSettings = worklogExportSettings.asExternalModel(),
        )

    companion object {
        fun fromExternalModel(config: WorkContextAdvancedConfig): WorkContextAdvancedConfigJson =
            WorkContextAdvancedConfigJson(
                worklogExportSettings =
                    WorklogExportSettingsJson.fromExternalModel(
                        config.worklogExportSettings,
                    ),
            )
    }
}

@Serializable
private data class WorklogExportSettingsJson(
    val roundWorkTimeTo: String? = null,
    val roundStartTimeTo: String? = null,
    val roundEndTimeTo: String? = null,
    val separateTasksBy: String = DefaultTaskSeparator,
    val cols: List<String> = emptyList(),
    val groupBy: String = WorklogGrouping.DATE.name,
) {
    fun asExternalModel(): WorklogExportSettings =
        WorklogExportSettings(
            roundWorkTimeTo = decodeRoundTimeOption(roundWorkTimeTo),
            roundStartTimeTo = decodeRoundTimeOption(roundStartTimeTo),
            roundEndTimeTo = decodeRoundTimeOption(roundEndTimeTo),
            separateTasksBy = separateTasksBy,
            cols = cols.map(::decodeWorklogColumn),
            groupBy = decodeEnumName(groupBy),
        )

    companion object {
        fun fromExternalModel(settings: WorklogExportSettings): WorklogExportSettingsJson =
            WorklogExportSettingsJson(
                roundWorkTimeTo = settings.roundWorkTimeTo?.value,
                roundStartTimeTo = settings.roundStartTimeTo?.value,
                roundEndTimeTo = settings.roundEndTimeTo?.value,
                separateTasksBy = settings.separateTasksBy,
                cols = settings.cols.map(WorklogColumn::name),
                groupBy = settings.groupBy.name,
            )
    }
}

@Serializable
private data class WorkContextThemeConfigJson(
    val isAutoContrast: Boolean? = null,
    val isDisableBackgroundTint: Boolean? = null,
    val primary: String? = null,
    val huePrimary: String? = null,
    val accent: String? = null,
    val hueAccent: String? = null,
    val warn: String? = null,
    val hueWarn: String? = null,
    val backgroundImageDark: String? = null,
    val backgroundImageLight: String? = null,
    val backgroundOverlayOpacity: Float? = null,
) {
    fun asExternalModel(): WorkContextThemeConfig =
        WorkContextThemeConfig(
            isAutoContrast = isAutoContrast,
            isDisableBackgroundTint = isDisableBackgroundTint,
            primary = primary,
            huePrimary = huePrimary,
            accent = accent,
            hueAccent = hueAccent,
            warn = warn,
            hueWarn = hueWarn,
            backgroundImageDark = backgroundImageDark,
            backgroundImageLight = backgroundImageLight,
            backgroundOverlayOpacity = backgroundOverlayOpacity,
        )

    companion object {
        fun fromExternalModel(config: WorkContextThemeConfig): WorkContextThemeConfigJson =
            WorkContextThemeConfigJson(
                isAutoContrast = config.isAutoContrast,
                isDisableBackgroundTint = config.isDisableBackgroundTint,
                primary = config.primary,
                huePrimary = config.huePrimary,
                accent = config.accent,
                hueAccent = config.hueAccent,
                warn = config.warn,
                hueWarn = config.hueWarn,
                backgroundImageDark = config.backgroundImageDark,
                backgroundImageLight = config.backgroundImageLight,
                backgroundOverlayOpacity = config.backgroundOverlayOpacity,
            )
    }
}

private fun decodeRoundTimeOption(value: String?): RoundTimeOption? =
    value?.let { wireValue ->
        decodeWireValue(
            value = wireValue,
            values = RoundTimeOption.entries,
            valueSelector = RoundTimeOption::value,
            typeName = "RoundTimeOption",
        )
    }

private fun decodeWorklogColumn(value: String): WorklogColumn = decodeEnumName(value)
