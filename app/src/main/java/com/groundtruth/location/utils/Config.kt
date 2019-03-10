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

    object Location {
        const val UPDATE_INTERVAL = (10 * 1000).toLong()
        const val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2
        const val MAX_WAIT_TIME = UPDATE_INTERVAL * 3
    }
}