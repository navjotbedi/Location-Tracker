package com.groundtruth.location.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import com.google.android.gms.location.LocationResult
import com.groundtruth.location.managers.DBManager
import com.groundtruth.location.network.AppRestService
import com.groundtruth.location.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LocationUpdateBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    private val dbManager: DBManager by inject()
    private val restService: AppRestService by inject()

    companion object {
        const val ACTION_LOCATION_UPDATES =
            "com.groundtruth.location.services.services.LocationUpdateBroadcastReceiver.ACTION_LOCATION_UPDATES"
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            if (ACTION_LOCATION_UPDATES == intent.action) {
                val result = LocationResult.extractResult(intent)
                if (result != null) {
                    val locations = result.locations
                    if (locations.isNotEmpty()) {
                        LogUtils.d(null, "Location Received -> $locations")
                        processReceivedLocation(locations)
                    }
                }
            }
        }
    }

    private fun processReceivedLocation(locations: List<Location>) {
        val insertSubscription = dbManager.insertLocations(locations) // Saving Locations
            .flatMap { dbManager.getLocationRequestModel() } // Receive all stored location
            .filter { it.locationArray.isNotEmpty() } // Filter if locations collected are less than required
            .flatMap { restService.sendLocation(it) } // Hit the API to send location
            .filter { it.isSuccessful } // Filter successful response
            .flatMap { dbManager.stashLocations() } // Delete all stored locations
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { success ->
                    println(success)
                },
                onError = { it.printStackTrace() },
                onComplete = { println("Done!") }
            )
    }

}