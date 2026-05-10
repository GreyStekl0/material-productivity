package dev.stekl0.materialproductivity.core.data.model

import dev.stekl0.materialproductivity.core.database.model.TagEntity
import dev.stekl0.materialproductivity.core.model.Tag
import dev.stekl0.materialproductivity.core.model.WorkContext
import dev.stekl0.materialproductivity.core.model.WorkContextType

internal fun TagEntity.asExternalModel(): Tag =
    Tag(
        id = id,
        title = title,
        taskIds = decodeStringList(taskIdsJson),
        icon = icon,
        color = color,
        created = created,
        modified = modified,
        advancedConfig = decodeAdvancedConfig(advancedConfigJson),
        theme = decodeThemeConfig(themeJson),
    )

internal fun Tag.asEntity(): TagEntity =
    TagEntity(
        id = id,
        title = title,
        taskIdsJson = encodeStringList(taskIds),
        icon = icon,
        color = color,
        created = created,
        modified = modified,
        advancedConfigJson = encodeAdvancedConfig(advancedConfig),
        themeJson = encodeThemeConfig(theme),
    )

internal fun Tag.asWorkContext(): WorkContext =
    WorkContext(
        id = id,
        title = title,
        type = WorkContextType.TAG,
        taskIds = taskIds,
        icon = icon,
        advancedConfig = advancedConfig,
        theme = theme,
    )
