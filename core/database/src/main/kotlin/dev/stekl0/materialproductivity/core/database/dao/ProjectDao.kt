package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface ProjectDao {
    @Query(value = "SELECT * FROM projects")
    public fun getProjectEntities(): Flow<List<ProjectEntity>>

    @Query(value = "SELECT * FROM projects")
    public suspend fun getOneOffProjectEntities(): List<ProjectEntity>

    @Query(
        value = """
            SELECT * FROM projects
            WHERE id = :projectId
        """,
    )
    public fun getProjectEntity(projectId: String): Flow<ProjectEntity?>

    @Query(
        value = """
            SELECT * FROM projects
            WHERE id IN (:ids)
        """,
    )
    public fun getProjectEntities(ids: List<String>): Flow<List<ProjectEntity>>

    @Upsert
    public suspend fun upsertProjects(projectEntities: List<ProjectEntity>)

    @Query(
        value = """
            DELETE FROM projects
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteProjects(ids: List<String>)
}
