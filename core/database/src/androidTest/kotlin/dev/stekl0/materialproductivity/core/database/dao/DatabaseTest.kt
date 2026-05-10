package dev.stekl0.materialproductivity.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.stekl0.materialproductivity.core.database.MpDatabase
import org.junit.After
import org.junit.Before

internal abstract class DatabaseTest {
    private lateinit var db: MpDatabase
    protected lateinit var noteDao: NoteDao
    protected lateinit var projectDao: ProjectDao
    protected lateinit var projectFolderDao: ProjectFolderDao
    protected lateinit var reminderDao: ReminderDao
    protected lateinit var tagDao: TagDao
    protected lateinit var taskDao: TaskDao
    protected lateinit var taskRepeatConfigDao: TaskRepeatConfigDao

    @Before
    fun setup() {
        db =
            run {
                val context = ApplicationProvider.getApplicationContext<Context>()
                Room
                    .inMemoryDatabaseBuilder(
                        context,
                        MpDatabase::class.java,
                    ).build()
            }
        noteDao = db.noteDao()
        projectDao = db.projectDao()
        projectFolderDao = db.projectFolderDao()
        reminderDao = db.reminderDao()
        tagDao = db.tagDao()
        taskDao = db.taskDao()
        taskRepeatConfigDao = db.taskRepeatConfigDao()
    }

    @After
    fun teardown() = db.close()
}
