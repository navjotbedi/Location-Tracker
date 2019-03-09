package com.groundtruth.location.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.groundtruth.location.utils.Config

class LocationUploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val ApiMa

    override fun doWork(): Result {
        Log.d("tttttt", "LocationUploadWorker: Started to work");
        val longitude = inputData.getDouble(Config.DBColumns.LATITUDE_COLUMN, 0.0)
        val latitude = inputData.getDouble(Config.DBColumns.LONGITUDE_COLUMN, 0.0)

        return Result.success()
    }

}