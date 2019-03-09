package com.groundtruth.location.models

import io.realm.RealmObject

open class Location(_latitude: Double? = null, _longitude: Double? = null) : RealmObject(){
    var latitude = _latitude
    var longitude = _longitude
}