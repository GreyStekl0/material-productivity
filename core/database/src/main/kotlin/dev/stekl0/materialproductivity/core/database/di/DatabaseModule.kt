package dev.stekl0.materialproductivity.core.database.di

import androidx.room.Room
import dev.stekl0.materialproductivity.core.database.MpDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.jvm.java

public fun databaseModule(): Module =
    module {
        single {
            Room
                .databaseBuilder(
                    context = androidContext(),
                    klass = MpDatabase::class.java,
                    name = "mp-database",
                ).build()
        }
    }
