package dev.stekl0.materialproductivity.core.data.di

import dev.stekl0.materialproductivity.core.data.repository.NotesRepository
import dev.stekl0.materialproductivity.core.data.repository.OfflineFirstNotesRepository
import dev.stekl0.materialproductivity.core.data.repository.OfflineFirstProjectsRepository
import dev.stekl0.materialproductivity.core.data.repository.OfflineFirstRemindersRepository
import dev.stekl0.materialproductivity.core.data.repository.OfflineFirstTagsRepository
import dev.stekl0.materialproductivity.core.data.repository.OfflineFirstTaskRepeatConfigsRepository
import dev.stekl0.materialproductivity.core.data.repository.OfflineFirstTasksRepository
import dev.stekl0.materialproductivity.core.data.repository.ProjectsRepository
import dev.stekl0.materialproductivity.core.data.repository.RemindersRepository
import dev.stekl0.materialproductivity.core.data.repository.TagsRepository
import dev.stekl0.materialproductivity.core.data.repository.TaskRepeatConfigsRepository
import dev.stekl0.materialproductivity.core.data.repository.TasksRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public fun dataModule(): Module =
    module {
        single<TasksRepository> {
            OfflineFirstTasksRepository(
                taskDao = get(),
                projectDao = get(),
                tagDao = get(),
            )
        }
        single<ProjectsRepository> {
            OfflineFirstProjectsRepository(
                projectDao = get(),
                projectFolderDao = get(),
                tagDao = get(),
            )
        }
        single<TagsRepository> {
            OfflineFirstTagsRepository(
                tagDao = get(),
            )
        }
        single<NotesRepository> {
            OfflineFirstNotesRepository(
                noteDao = get(),
                projectDao = get(),
            )
        }
        single<RemindersRepository> {
            OfflineFirstRemindersRepository(
                reminderDao = get(),
            )
        }
        single<TaskRepeatConfigsRepository> {
            OfflineFirstTaskRepeatConfigsRepository(
                taskRepeatConfigDao = get(),
            )
        }
    }
