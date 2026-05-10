package dev.stekl0.materialproductivity.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
public data class ProjectEntity(
    @PrimaryKey
    public val id: String,
    public val title: String,
    @ColumnInfo(name = "task_ids_json")
    public val taskIdsJson: String,
    @ColumnInfo(name = "backlog_task_ids_json")
    public val backlogTaskIdsJson: String,
    @ColumnInfo(name = "note_ids_json")
    public val noteIdsJson: String,
    @ColumnInfo(name = "is_archived")
    public val isArchived: Boolean,
    @ColumnInfo(name = "is_hidden_from_menu")
    public val isHiddenFromMenu: Boolean,
    @ColumnInfo(name = "is_enable_backlog")
    public val isEnableBacklog: Boolean,
    public val icon: String?,
    public val created: Long?,
    public val modified: Long?,
    @ColumnInfo(name = "folder_id")
    public val folderId: String?,
    @ColumnInfo(name = "advanced_config_json")
    public val advancedConfigJson: String,
    @ColumnInfo(name = "theme_json")
    public val themeJson: String,
    @ColumnInfo(name = "issue_integration_configs_json")
    public val issueIntegrationConfigsJson: String,
)

@Entity(tableName = "project_folders")
public data class ProjectFolderEntity(
    @PrimaryKey
    public val id: String,
    public val title: String,
    public val icon: String?,
    @ColumnInfo(name = "parent_id")
    public val parentId: String?,
    @ColumnInfo(name = "is_expanded")
    public val isExpanded: Boolean,
    public val created: Long,
    public val modified: Long?,
)
