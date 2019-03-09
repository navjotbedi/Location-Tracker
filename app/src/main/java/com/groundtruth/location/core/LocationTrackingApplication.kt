package com.groundtruth.location.core

import android.app.Application
import com.groundtruth.location.di.appModule
import com.groundtruth.location.di.networkModule
import org.koin.android.ext.android.startKoin

class LocationTrackingApplication: Application (){

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, networkModule))
    }

}