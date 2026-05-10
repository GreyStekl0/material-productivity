package dev.stekl0.materialproductivity.core.data.model

internal fun <T> List<T>.orderedByIds(
    ids: List<String>,
    idSelector: (T) -> String,
): List<T> {
    val entitiesById = associateBy(idSelector)
    return ids.mapNotNull(entitiesById::get)
}
