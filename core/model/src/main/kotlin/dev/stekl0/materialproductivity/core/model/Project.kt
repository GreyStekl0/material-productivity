package dev.stekl0.materialproductivity.core.model

public data class Project(
    public val id: String,
    public val title: String,
    public val taskIds: List<String> = emptyList(),
    public val backlogTaskIds: List<String> = emptyList(),
    public val noteIds: List<String> = emptyList(),
    public val isArchived: Boolean = false,
    public val isHiddenFromMenu: Boolean = false,
    public val isEnableBacklog: Boolean = false,
    public val icon: String? = null,
    public val created: Long? = null,
    public val modified: Long? = null,
    public val folderId: String? = null,
    public val advancedConfig: WorkContextAdvancedConfig = WorkContextAdvancedConfig(),
    public val theme: WorkContextThemeConfig = WorkContextThemeConfig(),
    public val issueIntegrationConfigs: Map<String, IssueIntegrationConfig> = emptyMap(),
)

public data class ProjectFolder(
    public val id: String,
    public val title: String,
    public val icon: String? = null,
    public val parentId: String? = null,
    public val isExpanded: Boolean = false,
    public val created: Long,
    public val modified: Long? = null,
)

public data class IssueIntegrationConfig(
    public val providerKey: String,
    public val isEnabled: Boolean,
)
