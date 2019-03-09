package com.groundtruth.location.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class LocationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val TAG = "LocationWorker"
    }

    override fun doWork(): Result {
        Log.d("tttttt", "LocationWorker: Started to work");
        return Result.success()
    }

}