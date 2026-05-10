package dev.stekl0.materialproductivity.core.data.repository

import dev.stekl0.materialproductivity.core.model.Note
import kotlinx.coroutines.flow.Flow

public interface NotesRepository {
    public fun getNotes(): Flow<List<Note>>

    public fun getNote(noteId: String): Flow<Note?>

    public fun getProjectNotes(projectId: String): Flow<List<Note>>

    public fun getInboxNotes(): Flow<List<Note>>

    public fun getPinnedToTodayNotes(): Flow<List<Note>>

    public suspend fun upsertNotes(notes: List<Note>)

    public suspend fun deleteNotes(ids: List<String>)
}
