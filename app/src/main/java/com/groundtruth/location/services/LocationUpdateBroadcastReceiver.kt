package com.groundtruth.location.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.LocationResult
import com.groundtruth.location.managers.DBManager
import com.groundtruth.location.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LocationUpdateBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_LOCATION_UPDATES =
            "com.groundtruth.location.services.services.LocationUpdateBroadcastReceiver.ACTION_LOCATION_UPDATES"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            val action = intent.action
            if (ACTION_LOCATION_UPDATES == action) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val locations = result.locations
                    LogUtils.d(null, "Location->$locations")
                    // Save Location to DB

                    if (locations.isNotEmpty()) {
                        val dbManager = DBManager(context)
                        val insertSubscription = dbManager.insertLocation(locations[0])
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(
                                onNext = { success ->
                                    if (success) {
                                        dbManager.getLocationRequestModel()
//                                            .flatMap { return@flatMap restService.sendLocation(it) }
                                            .subscribeOn(Schedulers.newThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeBy(
                                                onNext = { println(it) },
                                                onError = { it.printStackTrace() },
                                                onComplete = { println("Done!") }
                                            )
                                    }
                                },
                                onError = { it.printStackTrace() },
                                onComplete = { println("Done!") }
                            )
                    }

                }
            }
        }
    }

}