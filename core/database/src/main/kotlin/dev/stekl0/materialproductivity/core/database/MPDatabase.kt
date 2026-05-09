package dev.stekl0.materialproductivity.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
)
internal abstract class MPDatabase : RoomDatabase()
