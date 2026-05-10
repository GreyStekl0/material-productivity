package dev.stekl0.materialproductivity.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
public data class NoteEntity(
    @PrimaryKey
    public val id: String,
    @ColumnInfo(name = "project_id")
    public val projectId: String?,
    public val content: String,
    @ColumnInfo(name = "is_pinned_to_today")
    public val isPinnedToToday: Boolean,
    public val created: Long,
    public val modified: Long,
    @ColumnInfo(name = "img_url")
    public val imgUrl: String?,
    @ColumnInfo(name = "is_locked")
    public val isLocked: Boolean,
    @ColumnInfo(name = "background_color")
    public val backgroundColor: String?,
)
