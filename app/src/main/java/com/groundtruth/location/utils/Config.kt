package com.groundtruth.location.utils

class Config {
    object SharedPreferences {
        const val PROPERTY_PREF = "PREFERENCE_DEFAULT"

        const val PROPERTY_TRACKING_ENABLE_PREF = "PROPERTY_TRACKING_ENABLE_PREF"
    }

    object Endpoints {
        const val BASE_PATH = "/api/"

        const val SEND_LOCATION = BASE_PATH + "location/"
    }

    object DBColumns {
        const val LATITUDE_COLUMN = "latitude"
        const val LONGITUDE_COLUMN = "longitude"
    }
}