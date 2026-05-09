package dev.stekl0.materialproductivity.core.model

public data class Note(
    public val id: String,
    public val projectId: String?,
    public val content: String,
    public val isPinnedToToday: Boolean,
    public val created: Long,
    public val modified: Long,
    public val imgUrl: String? = null,
    public val isLocked: Boolean = false,
    public val backgroundColor: String? = null,
)
