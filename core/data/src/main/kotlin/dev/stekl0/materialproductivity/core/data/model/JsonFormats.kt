package dev.stekl0.materialproductivity.core.data.model

import kotlinx.serialization.json.Json

internal val DatabaseJson: Json =
    Json {
        encodeDefaults = true
        explicitNulls = false
        ignoreUnknownKeys = true
    }
