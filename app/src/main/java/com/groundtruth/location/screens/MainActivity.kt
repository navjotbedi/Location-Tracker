package com.groundtruth.location.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.groundtruth.location.BuildConfig
import com.groundtruth.location.R
import com.groundtruth.location.managers.LocationManager
import com.groundtruth.location.managers.PreferenceManager
import com.groundtruth.location.services.LocationUpdateBroadcastReceiver
import com.groundtruth.location.utils.Config
import com.groundtruth.location.utils.Constants.Companion.PERMISSIONS_REQUEST_LOCATION
import com.groundtruth.location.utils.LogUtils
import com.groundtruth.location.utils.Utils
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private var locationButton: TextView? = null
    private val locationManager: LocationManager by inject()
    private val preferenceManager: PreferenceManager by inject()
    private var googleApiClient: GoogleApiClient? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        buildGoogleApiClient()
        setupClickListeners()
    }

    private fun initUI(){
        locationButton = findViewById(R.id.location_btn)
        if(preferenceManager.isTrackingEnable() && isPermissionGranted()){
            locationManager.startLocationTracking(getPendingIntent())
        }
    }

    override fun onResume() {
        super.onResume()
        updateButtonText()
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, LocationUpdateBroadcastReceiver::class.java)
        intent.action = LocationUpdateBroadcastReceiver.ACTION_LOCATION_UPDATES
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    // UI Components

    private fun setupClickListeners() {
        findViewById<View>(R.id.location_btn).setOnClickListener {
            if (!isPermissionGranted()) {
                askPermission()
            } else {
                val isTrackingEnabled = preferenceManager.isTrackingEnable()
                if (isTrackingEnabled) {
                    locationManager.stopLocationTracking(getPendingIntent())
                } else {
                    locationManager.startLocationTracking(getPendingIntent())
                }
                preferenceManager.changeTrackingMode(!isTrackingEnabled)
                updateButtonText()
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
            locationManager.stopLocationTracking(getPendingIntent())
            locationButton?.setText(R.string.txt_grant_permission)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dev_info -> {
                Utils.openWebPage(Config.Extras.DEVELOPER_PROFILE, this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    // Google API Client

    private fun buildGoogleApiClient() {
        if (googleApiClient != null) {
            return
        }
        googleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, this)
            .addApi(LocationServices.API)
            .build()
    }


    override fun onConnected(p0: Bundle?) {
        LogUtils.i(null, getString(R.string.message_connection_successful))
    }

    override fun onConnectionSuspended(p0: Int) {
        Utils.showSnackbar(getString(R.string.error_connection_suspended), findViewById(R.id.activity_main))
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Utils.showSnackbar(
            getString(R.string.error_connecting_google) + ": " + connectionResult.errorMessage,
            findViewById(R.id.activity_main)
        )
    }


    // Permissions

    private fun askPermission() {
        if (!isPermissionGranted()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.ok) {
                        requestPermission()
                    }
                    .show()
            } else {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    updateButtonText()
                } else {
                    Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.settings) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                }
                return
            }
        }
    }

    private fun isPermissionGranted(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }
}
