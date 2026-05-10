package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.data.testdoubles.TestProjectDao
import dev.stekl0.materialproductivity.core.data.testdoubles.TestTagDao
import dev.stekl0.materialproductivity.core.data.testdoubles.TestTaskDao
import dev.stekl0.materialproductivity.core.data.testdoubles.testProjectEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testTagEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testTaskEntity
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.database.dao.TaskDao
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import dev.stekl0.materialproductivity.core.model.Task
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OfflineFirstTasksRepositoryTest {
    private lateinit var subject: OfflineFirstTasksRepository
    private lateinit var taskDao: TaskDao
    private lateinit var projectDao: ProjectDao
    private lateinit var tagDao: TagDao

    @BeforeEach
    fun setup() {
        taskDao = TestTaskDao()
        projectDao = TestProjectDao()
        tagDao = TestTagDao()
        subject =
            OfflineFirstTasksRepository(
                taskDao = taskDao,
                projectDao = projectDao,
                tagDao = tagDao,
            )
    }

    @Test
    fun projectTasks_areOrderedByProjectTaskIds() =
        runTest {
            taskDao.upsertTasks(testTaskEntities(projectId = ProjectId))
            projectDao.upsertProjects(
                listOf(
                    testProjectEntity(
                        id = ProjectId,
                        taskIdsJson = """["task-2","task-1"]""",
                    ),
                ),
            )

            assertEquals(
                listOf("task-2", "task-1"),
                subject.getProjectTasks(ProjectId).first().map(Task::id),
            )
        }

    @Test
    fun projectBacklogTasks_areOrderedByProjectBacklogTaskIds() =
        runTest {
            taskDao.upsertTasks(testTaskEntities(projectId = ProjectId))
            projectDao.upsertProjects(
                listOf(
                    testProjectEntity(
                        id = ProjectId,
                        backlogTaskIdsJson = """["task-3","task-1"]""",
                    ),
                ),
            )

            assertEquals(
                listOf("task-3", "task-1"),
                subject.getProjectBacklogTasks(ProjectId).first().map(Task::id),
            )
        }

    @Test
    fun tagTasks_areOrderedByTagTaskIds() =
        runTest {
            taskDao.upsertTasks(testTaskEntities())
            tagDao.upsertTags(
                listOf(
                    testTagEntity(
                        id = TagId,
                        taskIdsJson = """["task-3","task-2"]""",
                    ),
                ),
            )

            assertEquals(
                listOf("task-3", "task-2"),
                subject.getTagTasks(TagId).first().map(Task::id),
            )
        }

    @Test
    fun subTasks_areOrderedByParentSubTaskIds() =
        runTest {
            taskDao.upsertTasks(
                testTaskEntities() +
                    testTaskEntity(
                        id = ParentTaskId,
                        subTaskIdsJson = """["task-2","task-1"]""",
                    ),
            )

            assertEquals(
                listOf("task-2", "task-1"),
                subject.getSubTasks(ParentTaskId).first().map(Task::id),
            )
        }

    private fun testTaskEntities(projectId: String? = null): List<TaskEntity> =
        listOf(
            testTaskEntity(id = "task-1", projectId = projectId),
            testTaskEntity(id = "task-2", projectId = projectId),
            testTaskEntity(id = "task-3", projectId = projectId),
        )
}

private const val ProjectId = "project-id"
private const val TagId = "tag-id"
private const val ParentTaskId = "parent-task-id"
