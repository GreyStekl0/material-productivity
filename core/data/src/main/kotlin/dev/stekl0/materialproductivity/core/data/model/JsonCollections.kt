package dev.stekl0.materialproductivity.core.data.model

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

internal fun decodeStringList(json: String): List<String> =
    DatabaseJson.decodeFromString(
        ListSerializer(String.serializer()),
        json,
    )

internal fun encodeStringList(values: List<String>): String =
    DatabaseJson.encodeToString(
        ListSerializer(String.serializer()),
        values,
    )

internal fun decodeLongMap(json: String): Map<String, Long> =
    DatabaseJson.decodeFromString(
        MapSerializer(String.serializer(), Long.serializer()),
        json,
    )

internal fun encodeLongMap(values: Map<String, Long>): String =
    DatabaseJson.encodeToString(
        MapSerializer(String.serializer(), Long.serializer()),
        values,
    )
