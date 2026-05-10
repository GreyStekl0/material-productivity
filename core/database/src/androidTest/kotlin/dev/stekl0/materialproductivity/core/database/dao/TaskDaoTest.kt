package dev.stekl0.materialproductivity.core.database.dao

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

internal class TaskDaoTest : DatabaseTest() {
    @Test
    fun getTaskEntity_readsInsertedTask() =
        runTest {
            val task = testTaskEntity()

            taskDao.upsertTasks(listOf(task))

            assertEquals(task, taskDao.getTaskEntity(task.id).first())
        }

    @Test
    fun getInboxTaskEntities_returnsOnlyTasksWithoutProject() =
        runTest {
            val inboxTask = testTaskEntity(id = "inbox-task-id", projectId = null)
            val projectTask = testTaskEntity(id = "project-task-id", projectId = "project-id")

            taskDao.upsertTasks(listOf(inboxTask, projectTask))

            assertEquals(
                listOf(inboxTask),
                taskDao.getInboxTaskEntities().first(),
            )
        }
}
