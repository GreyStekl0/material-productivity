package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface ProjectFolderDao {
    @Query(value = "SELECT * FROM project_folders")
    public fun getProjectFolderEntities(): Flow<List<ProjectFolderEntity>>

    @Query(value = "SELECT * FROM project_folders")
    public suspend fun getOneOffProjectFolderEntities(): List<ProjectFolderEntity>

    @Query(
        value = """
            SELECT * FROM project_folders
            WHERE id = :folderId
        """,
    )
    public fun getProjectFolderEntity(folderId: String): Flow<ProjectFolderEntity?>

    @Upsert
    public suspend fun upsertProjectFolders(folderEntities: List<ProjectFolderEntity>)

    @Query(
        value = """
            DELETE FROM project_folders
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteProjectFolders(ids: List<String>)
}
