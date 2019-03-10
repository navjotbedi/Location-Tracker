package com.groundtruth.location.services

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocationUploadWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {
        const val TAG = "LocationUploadWorker"
    }

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        Log.d("tttttt", "LocationUploadWorker: Started to work")

        return Result.success()
    }

    private fun uploadData() {
//        dbManager.getLocationRequestModel()
//            .flatMap { return@flatMap restService.sendLocation(it) }
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onNext = { println(it) },
//                onError = { it.printStackTrace() },
//                onComplete = { println("Done!") }
//            ).dispose()
    }

}