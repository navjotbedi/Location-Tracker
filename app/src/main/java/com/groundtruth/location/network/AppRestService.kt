package com.groundtruth.location.network

import com.groundtruth.location.models.api.request.LocationRequestModel
import io.reactivex.Observable
import retrofit2.Response

class AppRestService(private val appRestApi: AppRestApi) {

    fun sendLocation(locationRequestPayload: LocationRequestModel): Observable<Response<com.groundtruth.location.models.api.response.LocationResponseModel>> {
        return appRestApi.sendLocation(locationRequestPayload)
    }

}