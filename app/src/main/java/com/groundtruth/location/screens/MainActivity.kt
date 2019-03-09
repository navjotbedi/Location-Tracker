package com.groundtruth.location.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.groundtruth.location.R
import com.groundtruth.location.managers.LocationManager
import com.groundtruth.location.managers.PreferenceManager
import com.groundtruth.location.utils.Constants.Companion.PERMISSIONS_REQUEST_LOCATION
import com.groundtruth.location.utils.Constants.Companion.PLAY_SERVICES_RESOLUTION_REQUEST
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private var locationButton: TextView? = null
    private val locationManager: LocationManager by inject()
    private val preferenceManager: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationButton = findViewById(R.id.location_btn)
        updateButtonText()

        findViewById<View>(R.id.location_btn).setOnClickListener {
            if (!isPermissionGranted()) {
                askPermission()
            } else {
                if (!preferenceManager.isTrackingEnable()) {
                    locationManager.startLocationTracking()
                } else {
                    locationManager.stopLocationTracking()
                }
                updateButtonText()
            }
        }
    }

    private fun askPermission() {
        if (!isPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    private fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
            } else {
                finish()
            }

            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    updateButtonText()
                } else {
                    // Permission Denied
                }
                return
            }
        }
    }

    private fun updateButtonText() {
        if (isPermissionGranted()) {
            if (!preferenceManager.isTrackingEnable()) {
                locationButton?.setText(R.string.txt_start_location_tracking)
            } else {
                locationButton?.setText(R.string.txt_stop_location_tracking)
            }
        } else {
            locationManager.stopLocationTracking()
            locationButton?.setText(R.string.txt_grant_permission)
        }
    }

    private fun isPermissionGranted(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return !(fineLocationPermission != PackageManager.PERMISSION_GRANTED && coarseLocationPermission != PackageManager.PERMISSION_GRANTED)
    }
}
