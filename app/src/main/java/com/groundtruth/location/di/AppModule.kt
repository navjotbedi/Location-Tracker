package com.groundtruth.location.di

import com.google.android.gms.location.LocationRequest
import com.groundtruth.location.managers.DBManager
import com.groundtruth.location.managers.LocationManager
import com.groundtruth.location.managers.PreferenceManager
import com.groundtruth.location.utils.Config
import org.koin.dsl.module.module

val appModule = module {

    single { PreferenceManager(get()) }

    single { DBManager(get()) }

    single { LocationManager(get(), get()) }

    single { provideLocationRequest() }
}

fun provideLocationRequest(): LocationRequest {
    val locationRequest = LocationRequest()

    locationRequest.interval = Config.Location.UPDATE_INTERVAL
    locationRequest.fastestInterval = Config.Location.FASTEST_UPDATE_INTERVAL
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    locationRequest.maxWaitTime = Config.Location.MAX_WAIT_TIME

    return locationRequest
}