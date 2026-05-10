package dev.stekl0.materialproductivity.core.database.di

import dev.stekl0.materialproductivity.core.database.MpDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

public fun daosModule(): Module =
    module {
        single { get<MpDatabase>().projectDao() }
        single { get<MpDatabase>().projectFolderDao() }
        single { get<MpDatabase>().tagDao() }
        single { get<MpDatabase>().taskDao() }
        single { get<MpDatabase>().noteDao() }
        single { get<MpDatabase>().reminderDao() }
        single { get<MpDatabase>().taskRepeatConfigDao() }
    }
