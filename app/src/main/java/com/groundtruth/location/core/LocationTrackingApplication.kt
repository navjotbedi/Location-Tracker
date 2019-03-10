package com.groundtruth.location.core

import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.groundtruth.location.di.appModule
import com.groundtruth.location.di.networkModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

class LocationTrackingApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, networkModule))
        Fabric.with(this, Crashlytics())
    }

}