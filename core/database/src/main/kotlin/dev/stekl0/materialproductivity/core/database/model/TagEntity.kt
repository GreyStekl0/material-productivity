package dev.stekl0.materialproductivity.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
public data class TagEntity(
    @PrimaryKey
    public val id: String,
    public val title: String,
    @ColumnInfo(name = "task_ids_json")
    public val taskIdsJson: String,
    public val icon: String?,
    public val color: String?,
    public val created: Long?,
    public val modified: Long?,
    @ColumnInfo(name = "advanced_config_json")
    public val advancedConfigJson: String,
    @ColumnInfo(name = "theme_json")
    public val themeJson: String,
)
