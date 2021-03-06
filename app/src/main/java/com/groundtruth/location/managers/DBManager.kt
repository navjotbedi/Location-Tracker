package com.groundtruth.location.managers

import android.content.Context
import com.groundtruth.location.models.Location
import com.groundtruth.location.models.api.request.LocationRequestModel
import com.groundtruth.location.utils.Config
import io.reactivex.Observable
import io.realm.Realm
import io.realm.kotlin.where

class DBManager(context: Context) {

    init {
        Realm.init(context)
    }

    // Store location to the database
    fun insertLocations(locations: List<android.location.Location>): Observable<Boolean> {
        return Observable.just(locations)
            .flatMap {
                return@flatMap Observable.create<Boolean> {
                    getRealmInstance().use { realm ->
                        var isDuplicate = true
                        realm.beginTransaction()
                        for (location in locations) {
                            if (!isDuplicate(location)) {
                                if (isDuplicate) isDuplicate = false
                                val dbLocation = Location(location.latitude, location.longitude)
                                realm.copyToRealm(dbLocation)
                            }
                        }
                        realm.commitTransaction()
                        it.onNext(!isDuplicate)
                    }
                }
            }
    }

    fun getLocationRequestModel(): Observable<LocationRequestModel> {
        return Observable.create {
            getRealmInstance().use { realm ->
                val savedLocations = realm.where<Location>().findAll()
                val locationRequestModel = LocationRequestModel()
                if (savedLocations.isNotEmpty() && savedLocations.count() > Config.Location.MIN_UPLOAD_LOCATION) {
                    savedLocations.forEach {
                        val location = Location(it.latitude, it.longitude)
                        locationRequestModel.locationArray += location
                    }
                }
                it.onNext(locationRequestModel)
            }
        }
    }

    // Delete all the saved location history
    fun stashLocations(): Observable<Boolean> {
        return Observable.create<Boolean> {
            getRealmInstance().use { realm ->
                realm.deleteAll()
                it.onNext(true)
            }
        }
    }

    private fun getRealmInstance(): Realm {
        return Realm.getDefaultInstance()
    }

    private fun isDuplicate(location: android.location.Location): Boolean {
        val realm = getRealmInstance()
        val resultCount = realm.where<com.groundtruth.location.models.Location>()
            .equalTo(Config.DBColumns.LATITUDE_COLUMN, location.latitude)
            .and()
            .equalTo(Config.DBColumns.LONGITUDE_COLUMN, location.longitude)
            .count()
        return resultCount > 0
    }

}