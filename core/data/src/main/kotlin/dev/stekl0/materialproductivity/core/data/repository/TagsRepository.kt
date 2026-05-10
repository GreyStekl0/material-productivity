package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.model.Tag
import kotlinx.coroutines.flow.Flow

public interface TagsRepository {
    public fun getTags(): Flow<List<Tag>>

    public fun getTag(tagId: String): Flow<Tag?>

    public suspend fun upsertTags(tags: List<Tag>)

    public suspend fun deleteTags(ids: List<String>)
}
