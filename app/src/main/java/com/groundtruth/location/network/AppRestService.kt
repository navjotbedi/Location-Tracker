package com.groundtruth.location.network

import com.groundtruth.location.models.api.request.LocationRequestModel
import com.groundtruth.location.models.api.response.LocationResponseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class AppRestService(private val appRestApi: AppRestApi) {

    fun sendLocation(locationRequestPayload: LocationRequestModel): Observable<Response<LocationResponseModel>> {
        return appRestApi.sendLocation(locationRequestPayload)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

}