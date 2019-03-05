package com.groundtruth.location.di

import com.groundtruth.location.managers.PreferenceManager
import org.koin.dsl.module.module

val appModule = module {

    single { PreferenceManager(get()) }

}