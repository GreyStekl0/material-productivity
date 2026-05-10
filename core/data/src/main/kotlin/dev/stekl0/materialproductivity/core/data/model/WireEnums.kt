package dev.stekl0.materialproductivity.core.data.model

import kotlin.enums.enumEntries

internal inline fun <reified T : Enum<T>> decodeEnumName(value: String): T =
    requireNotNull(enumEntries<T>().firstOrNull { it.name == value }) {
        "Unknown ${T::class.simpleName} value: $value"
    }

internal fun <T, V> decodeWireValue(
    value: V,
    values: Iterable<T>,
    valueSelector: (T) -> V,
    typeName: String,
): T =
    requireNotNull(values.firstOrNull { valueSelector(it) == value }) {
        "Unknown $typeName value: $value"
    }
