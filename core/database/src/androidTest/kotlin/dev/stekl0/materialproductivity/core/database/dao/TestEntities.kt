package dev.stekl0.materialproductivity.core.database.dao

import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity

internal fun testProjectEntity(id: String = "project-id"): ProjectEntity =
    ProjectEntity(
        id = id,
        title = "Project",
        taskIdsJson = "[]",
        backlogTaskIdsJson = "[]",
        noteIdsJson = "[]",
        isArchived = false,
        isHiddenFromMenu = false,
        isEnableBacklog = true,
        icon = null,
        created = null,
        modified = null,
        folderId = null,
        advancedConfigJson = "{}",
        themeJson = "{}",
        issueIntegrationConfigsJson = "{}",
    )

internal fun testProjectFolderEntity(id: String = "folder-id"): ProjectFolderEntity =
    ProjectFolderEntity(
        id = id,
        title = "Folder",
        icon = null,
        parentId = null,
        isExpanded = false,
        created = CreatedAt,
        modified = null,
    )

internal fun testTagEntity(id: String = "tag-id"): TagEntity =
    TagEntity(
        id = id,
        title = "Tag",
        taskIdsJson = "[]",
        icon = null,
        color = null,
        created = null,
        modified = null,
        advancedConfigJson = "{}",
        themeJson = "{}",
    )

internal fun testTaskEntity(
    id: String = "task-id",
    projectId: String? = null,
    parentId: String? = null,
): TaskEntity =
    TaskEntity(
        id = id,
        title = "Task",
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
        subTaskIdsJson = "[]",
        attachmentsJson = "[]",
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

internal fun testNoteEntity(
    id: String = "note-id",
    projectId: String? = null,
    isPinnedToToday: Boolean = false,
): NoteEntity =
    NoteEntity(
        id = id,
        projectId = projectId,
        content = "Note",
        isPinnedToToday = isPinnedToToday,
        created = CreatedAt,
        modified = ModifiedAt,
        imgUrl = null,
        isLocked = false,
        backgroundColor = null,
    )

internal fun testReminderEntity(
    id: String = "reminder-id",
    relatedId: String = "task-id",
    remindAt: Long = ReminderAt,
): ReminderEntity =
    ReminderEntity(
        id = id,
        remindAt = remindAt,
        title = "Reminder",
        type = "TASK",
        relatedId = relatedId,
        recurringConfigJson = "{}",
    )

internal fun testTaskRepeatConfigEntity(
    id: String = "repeat-id",
    projectId: String? = null,
    isPaused: Boolean = false,
): TaskRepeatConfigEntity =
    TaskRepeatConfigEntity(
        id = id,
        projectId = projectId,
        title = null,
        tagIdsJson = "[]",
        isPaused = isPaused,
        quickSetting = "DAILY",
        repeatCycle = "WEEKLY",
        repeatEvery = RepeatEvery,
        order = DefaultOrder,
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
private const val ReminderAt: Long = 3L
private const val NoTime: Long = 0L
private const val RepeatEvery: Int = 1
private const val DefaultOrder: Int = 0
