package com.groundtruth.location.managers

import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.groundtruth.location.services.LocationWorker
import java.util.concurrent.TimeUnit


class LocationManager {

    fun startLocationTracking() {
        val locationWork = PeriodicWorkRequest.Builder(
            LocationWorker::class.java, 5, TimeUnit.SECONDS
        ).addTag(LocationWorker.TAG).build()
        WorkManager.getInstance().enqueue(locationWork)
    }

    fun stopLocationTracking() {
        WorkManager.getInstance().cancelAllWorkByTag(LocationWorker.TAG)
    }

}