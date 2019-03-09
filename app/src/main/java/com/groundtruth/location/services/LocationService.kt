package com.groundtruth.location.services

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.JobIntentService
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class LocationService : JobIntentService(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest = LocationRequest.create()

    override fun onConnected(p0: Bundle?) {
        setUpLocationClientIfNeeded()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    override fun onHandleWork(intent: Intent) {
        LocationServices.getFusedLocationProviderClient(this).lastLocation
//            .requestLocationUpdates(mLocationRequest, LocationCallback(), null)
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {

    }

    private fun setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null)
            buildGoogleApiClient()
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        this.mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        this.mGoogleApiClient!!.connect()
    }

    override fun onLocationChanged(location: Location) {
        Log.d("token", location.latitude.toString())

    }

}