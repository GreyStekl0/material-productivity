package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.database.model.NoteEntity
import dev.stekl0.materialproductivity.core.model.Note

internal fun NoteEntity.asExternalModel(): Note =
    Note(
        id = id,
        projectId = projectId,
        content = content,
        isPinnedToToday = isPinnedToToday,
        created = created,
        modified = modified,
        imgUrl = imgUrl,
        isLocked = isLocked,
        backgroundColor = backgroundColor,
    )

internal fun Note.asEntity(): NoteEntity =
    NoteEntity(
        id = id,
        projectId = projectId,
        content = content,
        isPinnedToToday = isPinnedToToday,
        created = created,
        modified = modified,
        imgUrl = imgUrl,
        isLocked = isLocked,
        backgroundColor = backgroundColor,
    )
