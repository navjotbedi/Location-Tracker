package com.groundtruth.location.models

import io.realm.RealmObject

class Location(var _latitude: Double? = null, var _longitude: Double? = null) : RealmObject(){
    var latitude = _latitude
    var longitude = _longitude
}