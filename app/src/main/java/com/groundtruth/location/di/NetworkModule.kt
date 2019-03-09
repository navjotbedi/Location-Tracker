package com.groundtruth.location.di

import com.groundtruth.location.network.AppRestService
import org.koin.dsl.module.module

val networkModule = module {

    single { AppRestService(get()) }

}