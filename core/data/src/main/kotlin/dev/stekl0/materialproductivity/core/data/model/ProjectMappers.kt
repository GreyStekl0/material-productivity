package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import dev.stekl0.materialproductivity.core.model.IssueIntegrationConfig
import dev.stekl0.materialproductivity.core.model.Project
import dev.stekl0.materialproductivity.core.model.ProjectFolder
import dev.stekl0.materialproductivity.core.model.WorkContext
import dev.stekl0.materialproductivity.core.model.WorkContextType
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

internal fun ProjectEntity.asExternalModel(): Project =
    Project(
        id = id,
        title = title,
        taskIds = decodeStringList(taskIdsJson),
        backlogTaskIds = decodeStringList(backlogTaskIdsJson),
        noteIds = decodeStringList(noteIdsJson),
        isArchived = isArchived,
        isHiddenFromMenu = isHiddenFromMenu,
        isEnableBacklog = isEnableBacklog,
        icon = icon,
        created = created,
        modified = modified,
        folderId = folderId,
        advancedConfig = decodeAdvancedConfig(advancedConfigJson),
        theme = decodeThemeConfig(themeJson),
        issueIntegrationConfigs = decodeIssueIntegrationConfigs(issueIntegrationConfigsJson),
    )

internal fun Project.asEntity(): ProjectEntity =
    ProjectEntity(
        id = id,
        title = title,
        taskIdsJson = encodeStringList(taskIds),
        backlogTaskIdsJson = encodeStringList(backlogTaskIds),
        noteIdsJson = encodeStringList(noteIds),
        isArchived = isArchived,
        isHiddenFromMenu = isHiddenFromMenu,
        isEnableBacklog = isEnableBacklog,
        icon = icon,
        created = created,
        modified = modified,
        folderId = folderId,
        advancedConfigJson = encodeAdvancedConfig(advancedConfig),
        themeJson = encodeThemeConfig(theme),
        issueIntegrationConfigsJson = encodeIssueIntegrationConfigs(issueIntegrationConfigs),
    )

internal fun Project.asWorkContext(): WorkContext =
    WorkContext(
        id = id,
        title = title,
        type = WorkContextType.PROJECT,
        taskIds = taskIds,
        noteIds = noteIds,
        backlogTaskIds = backlogTaskIds,
        icon = icon,
        isEnableBacklog = isEnableBacklog,
        advancedConfig = advancedConfig,
        theme = theme,
    )

internal fun ProjectFolderEntity.asExternalModel(): ProjectFolder =
    ProjectFolder(
        id = id,
        title = title,
        icon = icon,
        parentId = parentId,
        isExpanded = isExpanded,
        created = created,
        modified = modified,
    )

internal fun ProjectFolder.asEntity(): ProjectFolderEntity =
    ProjectFolderEntity(
        id = id,
        title = title,
        icon = icon,
        parentId = parentId,
        isExpanded = isExpanded,
        created = created,
        modified = modified,
    )

private fun decodeIssueIntegrationConfigs(json: String): Map<String, IssueIntegrationConfig> =
    DatabaseJson
        .decodeFromString(
            MapSerializer(String.serializer(), IssueIntegrationConfigJson.serializer()),
            json,
        ).mapValues { (providerKey, config) -> config.asExternalModel(providerKey) }

private fun encodeIssueIntegrationConfigs(configs: Map<String, IssueIntegrationConfig>): String =
    DatabaseJson.encodeToString(
        MapSerializer(String.serializer(), IssueIntegrationConfigJson.serializer()),
        configs.mapValues { (_, config) -> IssueIntegrationConfigJson.fromExternalModel(config) },
    )

@Serializable
private data class IssueIntegrationConfigJson(
    val providerKey: String? = null,
    val isEnabled: Boolean = false,
) {
    fun asExternalModel(fallbackProviderKey: String): IssueIntegrationConfig =
        IssueIntegrationConfig(
            providerKey = providerKey ?: fallbackProviderKey,
            isEnabled = isEnabled,
        )

    companion object {
        fun fromExternalModel(config: IssueIntegrationConfig): IssueIntegrationConfigJson =
            IssueIntegrationConfigJson(
                providerKey = config.providerKey,
                isEnabled = config.isEnabled,
            )
    }
}
