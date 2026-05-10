package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.testdoubles.TestProjectDao
import dev.stekl0.materialproductivity.core.data.testdoubles.TestProjectFolderDao
import dev.stekl0.materialproductivity.core.data.testdoubles.TestTagDao
import dev.stekl0.materialproductivity.core.data.testdoubles.testProjectEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testProjectFolderEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testTagEntity
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectFolderDao
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.model.WorkContextType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfflineFirstProjectsRepositoryTest {
    private lateinit var subject: OfflineFirstProjectsRepository
    private lateinit var projectDao: ProjectDao
    private lateinit var projectFolderDao: ProjectFolderDao
    private lateinit var tagDao: TagDao

    @BeforeEach
    fun setup() {
        projectDao = TestProjectDao()
        projectFolderDao = TestProjectFolderDao()
        tagDao = TestTagDao()
        subject =
            OfflineFirstProjectsRepository(
                projectDao = projectDao,
                projectFolderDao = projectFolderDao,
                tagDao = tagDao,
            )
    }

    @Test
    fun projectsRepository_projectsStream_isBackedByProjectDao() =
        runTest {
            projectDao.upsertProjects(listOf(testProjectEntity(id = "project-id")))

            assertEquals(
                listOf("project-id"),
                subject.getProjects().first().map { it.id },
            )
        }

    @Test
    fun projectsRepository_projectFoldersStream_isBackedByProjectFolderDao() =
        runTest {
            projectFolderDao.upsertProjectFolders(listOf(testProjectFolderEntity(id = "folder-id")))

            assertEquals(
                listOf("folder-id"),
                subject.getProjectFolders().first().map { it.id },
            )
        }

    @Test
    fun workContexts_mergeProjectsAndTags() =
        runTest {
            projectDao.upsertProjects(listOf(testProjectEntity(id = "project-id")))
            tagDao.upsertTags(listOf(testTagEntity(id = "tag-id")))

            val contexts = subject.getWorkContexts().first()

            assertEquals(
                listOf("project-id", "tag-id"),
                contexts.map { it.id },
            )
            assertEquals(
                listOf(WorkContextType.PROJECT, WorkContextType.TAG),
                contexts.map { it.type },
            )
        }
}
