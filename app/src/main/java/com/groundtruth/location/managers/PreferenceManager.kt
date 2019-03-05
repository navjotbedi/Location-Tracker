package com.groundtruth.location.managers

import android.content.Context
import com.groundtruth.location.utils.Config

class PreferenceManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        Config.SharedPreferences.PROPERTY_PREF,
        Context.MODE_PRIVATE
    )

    fun savePreference(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanPreference(prefName: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(prefName, defaultValue)

}