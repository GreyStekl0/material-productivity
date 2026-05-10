package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class TagDaoTest : DatabaseTest() {
    @Test
    fun getTagEntity_readsInsertedTag() =
        runTest {
            val tag = testTagEntity()

            tagDao.upsertTags(listOf(tag))

            assertEquals(tag, tagDao.getTagEntity(tag.id).first())
        }

    @Test
    fun getTagEntitiesByIds_returnsOnlyRequestedTags() =
        runTest {
            val requestedTag = testTagEntity(id = "requested-tag-id")
            val otherTag = testTagEntity(id = "other-tag-id")

            tagDao.upsertTags(listOf(requestedTag, otherTag))

            assertEquals(
                setOf(requestedTag),
                tagDao
                    .getTagEntities(
                        listOf(requestedTag.id),
                    ).first()
                    .toSet(),
            )
        }
}
