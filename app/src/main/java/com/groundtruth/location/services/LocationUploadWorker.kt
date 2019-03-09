package com.groundtruth.location.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.groundtruth.location.managers.DBManager
import com.groundtruth.location.network.AppRestService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LocationUploadWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val restService: AppRestService,
    private val dbManager: DBManager
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Log.d("tttttt", "LocationUploadWorker: Started to work")

        dbManager.getLocationRequestModel()
            .flatMap { return@flatMap restService.sendLocation(it) }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { println(it) },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            ).dispose()

        return Result.success()
    }

}