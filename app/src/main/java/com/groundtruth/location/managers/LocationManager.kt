package com.groundtruth.location.managers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationManager(private val context: Context, private val locationRequest: LocationRequest) {

    @SuppressLint("MissingPermission")
    fun startLocationTracking(
        pendingIntent: PendingIntent
    ) {
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locationRequest, pendingIntent)
    }

    fun stopLocationTracking(
        pendingIntent: PendingIntent
    ) {
        LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(pendingIntent)
    }


}