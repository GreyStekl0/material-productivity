package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ProjectDaoTest : DatabaseTest() {
    @Test
    fun getProjectEntity_readsInsertedProject() =
        runTest {
            val project = testProjectEntity()

            projectDao.upsertProjects(listOf(project))

            assertEquals(project, projectDao.getProjectEntity(project.id).first())
        }

    @Test
    fun getProjectEntitiesByIds_returnsOnlyRequestedProjects() =
        runTest {
            val requestedProject = testProjectEntity(id = "requested-project-id")
            val otherProject = testProjectEntity(id = "other-project-id")

            projectDao.upsertProjects(listOf(requestedProject, otherProject))

            assertEquals(
                setOf(requestedProject),
                projectDao
                    .getProjectEntities(
                        listOf(requestedProject.id),
                    ).first()
                    .toSet(),
            )
        }
}
