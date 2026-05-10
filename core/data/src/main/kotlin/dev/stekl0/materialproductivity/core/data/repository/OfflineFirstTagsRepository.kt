package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.model.asEntity
import dev.stekl0.materialproductivity.core.data.model.asExternalModel
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class OfflineFirstTagsRepository(
    private val tagDao: TagDao,
) : TagsRepository {
    override fun getTags(): Flow<List<Tag>> =
        tagDao
            .getTagEntities()
            .map { entities -> entities.map(TagEntity::asExternalModel) }

    override fun getTag(tagId: String): Flow<Tag?> =
        tagDao
            .getTagEntity(tagId)
            .map { entity -> entity?.asExternalModel() }

    override suspend fun upsertTags(tags: List<Tag>) {
        tagDao.upsertTags(tags.map(Tag::asEntity))
    }

    override suspend fun deleteTags(ids: List<String>) {
        tagDao.deleteTags(ids)
    }
}
