package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.data.testdoubles.testProjectEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testReminderEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testTaskEntity
import dev.stekl0.materialproductivity.core.data.testdoubles.testTaskRepeatConfigEntity
import dev.stekl0.materialproductivity.core.model.IssueIntegrationConfig
import dev.stekl0.materialproductivity.core.model.MonthlyWeekOfMonth
import dev.stekl0.materialproductivity.core.model.MonthlyWeekday
import dev.stekl0.materialproductivity.core.model.Project
import dev.stekl0.materialproductivity.core.model.RepeatCycle
import dev.stekl0.materialproductivity.core.model.RepeatQuickSetting
import dev.stekl0.materialproductivity.core.model.RoundTimeOption
import dev.stekl0.materialproductivity.core.model.SubTaskTemplate
import dev.stekl0.materialproductivity.core.model.Task
import dev.stekl0.materialproductivity.core.model.TaskAttachment
import dev.stekl0.materialproductivity.core.model.TaskAttachmentType
import dev.stekl0.materialproductivity.core.model.TaskIssue
import dev.stekl0.materialproductivity.core.model.TaskReminderOption
import dev.stekl0.materialproductivity.core.model.TaskRepeatConfig
import dev.stekl0.materialproductivity.core.model.WorkContextAdvancedConfig
import dev.stekl0.materialproductivity.core.model.WorkContextThemeConfig
import dev.stekl0.materialproductivity.core.model.WorklogColumn
import dev.stekl0.materialproductivity.core.model.WorklogExportSettings
import dev.stekl0.materialproductivity.core.model.WorklogGrouping
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class ModelMappersTest {
    @Test
    fun taskMapper_roundTripsAttachmentsAndIssueJson() {
        val task =
            Task(
                id = "task-id",
                title = "Task",
                projectId = "project-id",
                created = CreatedAt,
                timeSpentOnDay = mapOf("2026-05-10" to 1_000L),
                tagIds = listOf("tag-1", "tag-2"),
                subTaskIds = listOf("sub-task-1"),
                attachments =
                    listOf(
                        TaskAttachment(
                            id = "attachment-id",
                            type = TaskAttachmentType.COMMAND,
                            title = "Run",
                            path = "echo ok",
                            icon = "terminal",
                        ),
                    ),
                issue =
                    TaskIssue(
                        issueId = "issue-id",
                        issueProviderId = "github",
                        issueType = "issue",
                        issueWasUpdated = true,
                        issueLastUpdated = ModifiedAt,
                        issueAttachmentNr = 1,
                        issueTimeTracked = mapOf("2026-05-10" to 2_000L),
                        issuePoints = 3,
                    ),
            )

        assertEquals(task, task.asEntity().asExternalModel())
    }

    @Test
    fun projectMapper_roundTripsAdvancedThemeAndIssueIntegrationConfigJson() {
        val project =
            Project(
                id = "project-id",
                title = "Project",
                taskIds = listOf("task-1", "task-2"),
                backlogTaskIds = listOf("task-3"),
                noteIds = listOf("note-1"),
                isEnableBacklog = true,
                advancedConfig =
                    WorkContextAdvancedConfig(
                        worklogExportSettings =
                            WorklogExportSettings(
                                roundWorkTimeTo = RoundTimeOption.QUARTER,
                                roundStartTimeTo = RoundTimeOption.MINUTES_5,
                                roundEndTimeTo = RoundTimeOption.HOUR,
                                separateTasksBy = " | ",
                                cols = listOf(WorklogColumn.DATE, WorklogColumn.TITLES),
                                groupBy = WorklogGrouping.TASK,
                            ),
                    ),
                theme =
                    WorkContextThemeConfig(
                        isAutoContrast = true,
                        primary = "#ffffff",
                        accent = "#000000",
                        backgroundOverlayOpacity = 0.5f,
                    ),
                issueIntegrationConfigs =
                    mapOf(
                        "github" to
                            IssueIntegrationConfig(
                                providerKey = "github",
                                isEnabled = true,
                            ),
                    ),
            )

        assertEquals(project, project.asEntity().asExternalModel())
    }

    @Test
    fun taskRepeatConfigMapper_roundTripsRepeatJson() {
        val repeatConfig =
            TaskRepeatConfig(
                id = "repeat-id",
                projectId = "project-id",
                title = "Repeat",
                tagIds = listOf("tag-1"),
                quickSetting = RepeatQuickSetting.MONTHLY_NTH_WEEKDAY,
                repeatCycle = RepeatCycle.MONTHLY,
                repeatEvery = 2,
                order = 3,
                remindAt = TaskReminderOption.M15,
                monday = true,
                monthlyWeekOfMonth = MonthlyWeekOfMonth.SECOND,
                monthlyWeekday = MonthlyWeekday.MONDAY,
                subTaskTemplates =
                    listOf(
                        SubTaskTemplate(
                            title = "Sub task",
                            timeEstimate = 1_000L,
                            notes = "Notes",
                        ),
                    ),
                deletedInstanceDates = listOf("2026-05-10"),
                skipOverdue = true,
            )

        assertEquals(repeatConfig, repeatConfig.asEntity().asExternalModel())
    }

    @Test
    fun taskMapper_unknownAttachmentType_throws() {
        val task =
            testTaskEntity(
                id = "task-id",
                attachmentsJson = """[{"type":"UNKNOWN"}]""",
            )

        assertThrows(IllegalArgumentException::class.java) {
            task.asExternalModel().let {}
        }
    }

    @Test
    fun reminderMapper_unknownReminderType_throws() {
        val reminder =
            testReminderEntity(
                id = "reminder-id",
                remindAt = CreatedAt,
            ).copy(type = "UNKNOWN")

        assertThrows(IllegalArgumentException::class.java) {
            reminder.asExternalModel().let {}
        }
    }

    @Test
    fun taskRepeatConfigMapper_unknownRepeatWireValues_throw() {
        val repeatConfig =
            testTaskRepeatConfigEntity(
                id = "repeat-id",
            ).copy(quickSetting = "UNKNOWN")

        assertThrows(IllegalArgumentException::class.java) {
            repeatConfig.asExternalModel().let {}
        }
    }

    @Test
    fun projectMapper_unknownWorklogGrouping_throws() {
        val project =
            testProjectEntity(
                id = "project-id",
            ).copy(
                advancedConfigJson = """{"worklogExportSettings":{"groupBy":"UNKNOWN"}}""",
            )

        assertThrows(IllegalArgumentException::class.java) {
            project.asExternalModel().let {}
        }
    }
}

private const val CreatedAt: Long = 1L
private const val ModifiedAt: Long = 2L
