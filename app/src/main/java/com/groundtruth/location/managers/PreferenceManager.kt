package com.groundtruth.location.managers

import android.content.Context
import com.groundtruth.location.utils.Config
import com.groundtruth.location.utils.Config.SharedPreferences.PROPERTY_TRACKING_ENABLE_PREF

class PreferenceManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        Config.SharedPreferences.PROPERTY_PREF,
        Context.MODE_PRIVATE
    )

    private fun savePreference(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBooleanPreference(prefName: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(prefName, defaultValue)

    fun isTrackingEnable(): Boolean{
        return getBooleanPreference(PROPERTY_TRACKING_ENABLE_PREF)
    }

    fun changeTrackingMode(isTrackingEnabled: Boolean){
        savePreference(PROPERTY_TRACKING_ENABLE_PREF, isTrackingEnabled)
    }
}