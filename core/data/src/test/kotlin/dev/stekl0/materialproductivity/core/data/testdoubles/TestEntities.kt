package dev.stekl0.materialproductivity.core.data.testdoubles

import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity

internal fun testTaskEntity(
    id: String,
    projectId: String? = null,
    parentId: String? = null,
    subTaskIdsJson: String = "[]",
    attachmentsJson: String = "[]",
): TaskEntity =
    TaskEntity(
        id = id,
        title = "Task $id",
        projectId = projectId,
        created = CreatedAt,
        modified = null,
        notes = null,
        timeEstimate = NoTime,
        timeSpent = NoTime,
        timeSpentOnDayJson = "{}",
        isDone = false,
        doneOn = null,
        tagIdsJson = "[]",
        parentId = parentId,
        subTaskIdsJson = subTaskIdsJson,
        attachmentsJson = attachmentsJson,
        remindAt = null,
        reminderId = null,
        dueDay = null,
        dueWithTime = null,
        hasPlannedTime = false,
        deadlineDay = null,
        deadlineWithTime = null,
        deadlineRemindAt = null,
        repeatCfgId = null,
        hideSubTasksMode = null,
        issueJson = null,
    )

internal fun testProjectEntity(
    id: String,
    taskIdsJson: String = "[]",
    backlogTaskIdsJson: String = "[]",
    noteIdsJson: String = "[]",
): ProjectEntity =
    ProjectEntity(
        id = id,
        title = "Project $id",
        taskIdsJson = taskIdsJson,
        backlogTaskIdsJson = backlogTaskIdsJson,
        noteIdsJson = noteIdsJson,
        isArchived = false,
        isHiddenFromMenu = false,
        isEnableBacklog = true,
        icon = null,
        created = CreatedAt,
        modified = null,
        folderId = null,
        advancedConfigJson = "{}",
        themeJson = "{}",
        issueIntegrationConfigsJson = "{}",
    )

internal fun testProjectFolderEntity(id: String): ProjectFolderEntity =
    ProjectFolderEntity(
        id = id,
        title = "Folder $id",
        icon = null,
        parentId = null,
        isExpanded = false,
        created = CreatedAt,
        modified = null,
    )

internal fun testTagEntity(
    id: String,
    taskIdsJson: String = "[]",
): TagEntity =
    TagEntity(
        id = id,
        title = "Tag $id",
        taskIdsJson = taskIdsJson,
        icon = null,
        color = null,
        created = CreatedAt,
        modified = null,
        advancedConfigJson = "{}",
        themeJson = "{}",
    )

internal fun testNoteEntity(
    id: String,
    projectId: String? = null,
    isPinnedToToday: Boolean = false,
): NoteEntity =
    NoteEntity(
        id = id,
        projectId = projectId,
        content = "Note $id",
        isPinnedToToday = isPinnedToToday,
        created = CreatedAt,
        modified = ModifiedAt,
        imgUrl = null,
        isLocked = false,
        backgroundColor = null,
    )

internal fun testReminderEntity(
    id: String,
    remindAt: Long,
    relatedId: String = "task-id",
): ReminderEntity =
    ReminderEntity(
        id = id,
        remindAt = remindAt,
        title = "Reminder $id",
        type = "TASK",
        relatedId = relatedId,
        recurringConfigJson = "{}",
    )

internal fun testTaskRepeatConfigEntity(
    id: String,
    projectId: String? = null,
    order: Int = 0,
    isPaused: Boolean = false,
): TaskRepeatConfigEntity =
    TaskRepeatConfigEntity(
        id = id,
        projectId = projectId,
        title = "Repeat $id",
        tagIdsJson = "[]",
        isPaused = isPaused,
        quickSetting = "DAILY",
        repeatCycle = "WEEKLY",
        repeatEvery = 1,
        order = order,
        defaultEstimate = null,
        startDate = null,
        startTime = null,
        remindAt = null,
        lastTaskCreation = null,
        lastTaskCreationDay = null,
        monday = null,
        tuesday = null,
        wednesday = null,
        thursday = null,
        friday = null,
        saturday = null,
        sunday = null,
        monthlyWeekOfMonth = null,
        monthlyWeekday = null,
        notes = null,
        shouldInheritSubtasks = false,
        repeatFromCompletionDate = false,
        disableAutoUpdateSubtasks = false,
        subTaskTemplatesJson = "[]",
        deletedInstanceDatesJson = "[]",
        skipOverdue = false,
    )

private const val CreatedAt: Long = 1L
private const val ModifiedAt: Long = 2L
private const val NoTime: Long = 0L
