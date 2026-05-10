package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ProjectFolderDaoTest : DatabaseTest() {
    @Test
    fun getProjectFolderEntity_readsInsertedProjectFolder() =
        runTest {
            val folder = testProjectFolderEntity()

            projectFolderDao.upsertProjectFolders(listOf(folder))

            assertEquals(folder, projectFolderDao.getProjectFolderEntity(folder.id).first())
        }

    @Test
    fun deleteProjectFolders_removesOnlyRequestedProjectFolders() =
        runTest {
            val deletedFolder = testProjectFolderEntity(id = "deleted-folder-id")
            val keptFolder = testProjectFolderEntity(id = "kept-folder-id")

            projectFolderDao.upsertProjectFolders(listOf(deletedFolder, keptFolder))
            projectFolderDao.deleteProjectFolders(listOf(deletedFolder.id))

            assertEquals(
                listOf(keptFolder),
                projectFolderDao.getOneOffProjectFolderEntities(),
            )
        }
}
