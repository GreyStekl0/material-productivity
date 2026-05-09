package dev.stekl0.materialproductivity.core.model

public data class Tag(
    public val id: String,
    public val title: String,
    public val taskIds: List<String> = emptyList(),
    public val icon: String? = null,
    public val color: String? = null,
    public val created: Long? = null,
    public val modified: Long? = null,
    public val advancedConfig: WorkContextAdvancedConfig = WorkContextAdvancedConfig(),
    public val theme: WorkContextThemeConfig = WorkContextThemeConfig(),
)
