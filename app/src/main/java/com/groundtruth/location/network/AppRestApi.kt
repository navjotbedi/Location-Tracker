package com.groundtruth.location.network

import com.groundtruth.location.models.api.request.LocationRequestModel
import com.groundtruth.location.models.api.response.LocationResponseModel
import com.groundtruth.location.utils.Config
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AppRestApi {
    @POST(Config.Endpoints.SEND_LOCATION)
    fun sendLocation(@Body data: LocationRequestModel): Observable<Response<LocationResponseModel>>
}