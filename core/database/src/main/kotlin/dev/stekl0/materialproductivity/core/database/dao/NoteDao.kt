package dev.stekl0.materialproductivity.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface NoteDao {
    @Query(value = "SELECT * FROM notes")
    public fun getNoteEntities(): Flow<List<NoteEntity>>

    @Query(value = "SELECT * FROM notes")
    public suspend fun getOneOffNoteEntities(): List<NoteEntity>

    @Query(
        value = """
            SELECT * FROM notes
            WHERE id = :noteId
        """,
    )
    public fun getNoteEntity(noteId: String): Flow<NoteEntity?>

    @Query(
        value = """
            SELECT * FROM notes
            WHERE id IN (:ids)
        """,
    )
    public fun getNoteEntities(ids: List<String>): Flow<List<NoteEntity>>

    @Query(
        value = """
            SELECT * FROM notes
            WHERE project_id = :projectId
        """,
    )
    public fun getNoteEntitiesByProjectId(projectId: String): Flow<List<NoteEntity>>

    @Query(
        value = """
            SELECT * FROM notes
            WHERE project_id IS NULL
        """,
    )
    public fun getInboxNoteEntities(): Flow<List<NoteEntity>>

    @Query(
        value = """
            SELECT * FROM notes
            WHERE is_pinned_to_today = 1
        """,
    )
    public fun getPinnedToTodayNoteEntities(): Flow<List<NoteEntity>>

    @Upsert
    public suspend fun upsertNotes(noteEntities: List<NoteEntity>)

    @Query(
        value = """
            DELETE FROM notes
            WHERE id IN (:ids)
        """,
    )
    public suspend fun deleteNotes(ids: List<String>)
}
