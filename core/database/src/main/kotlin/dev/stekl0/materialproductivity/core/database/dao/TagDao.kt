package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface TagDao {
    @Query(value = "SELECT * FROM tags")
    public fun getTagEntities(): Flow<List<TagEntity>>

    @Query(value = "SELECT * FROM tags")
    public suspend fun getOneOffTagEntities(): List<TagEntity>

    @Query(
        value = """
            SELECT * FROM tags
            WHERE id = :tagId
        """,
    )
    public fun getTagEntity(tagId: String): Flow<TagEntity?>

    @Query(
        value = """
            SELECT * FROM tags
            WHERE id IN (:ids)
        """,
    )
    public fun getTagEntities(ids: List<String>): Flow<List<TagEntity>>

    @Upsert
    public suspend fun upsertTags(tagEntities: List<TagEntity>)

    @Query(
        value = """
            DELETE FROM tags
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteTags(ids: List<String>)
}
