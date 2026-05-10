package dev.stekl0.materialproductivity.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.stekl0.materialproductivity.core.database.dao.NoteDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectDao
import dev.stekl0.materialproductivity.core.database.dao.ProjectFolderDao
import dev.stekl0.materialproductivity.core.database.dao.ReminderDao
import dev.stekl0.materialproductivity.core.database.dao.TagDao
import dev.stekl0.materialproductivity.core.database.dao.TaskDao
import dev.stekl0.materialproductivity.core.database.dao.TaskRepeatConfigDao
import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectEntity
import dev.stekl0.materialproductivity.core.database.model.ProjectFolderEntity
import dev.stekl0.materialproductivity.core.database.model.ReminderEntity
import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.database.model.TaskEntity
import dev.stekl0.materialproductivity.core.database.model.TaskRepeatConfigEntity

@Database(
    entities = [
        ProjectEntity::class,
        ProjectFolderEntity::class,
        TagEntity::class,
        TaskEntity::class,
        NoteEntity::class,
        ReminderEntity::class,
        TaskRepeatConfigEntity::class,
    ],
    version = 1,
)
internal abstract class MpDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao

    abstract fun projectFolderDao(): ProjectFolderDao

    abstract fun tagDao(): TagDao

    abstract fun taskDao(): TaskDao

    abstract fun noteDao(): NoteDao

    abstract fun reminderDao(): ReminderDao

    abstract fun taskRepeatConfigDao(): TaskRepeatConfigDao
}
