package com.groundtruth.location.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.groundtruth.location.BuildConfig
import com.groundtruth.location.network.AppRestApi
import com.groundtruth.location.network.AppRestService
import com.groundtruth.location.network.CustomLoggingInterceptor
import com.groundtruth.location.utils.Config
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { AppRestService(get()) }

    single { provideRestApi(get(), get()) }

    single { provideGson() }

    single { provideOkHttpClient() }
}

private fun configureRetrofit(url: String, okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideGson(): Gson {
    val builder = GsonBuilder()
    builder.disableHtmlEscaping()
    return builder.create()
}

fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient().newBuilder()
    builder.readTimeout(30, TimeUnit.SECONDS)
    builder.connectTimeout(30, TimeUnit.SECONDS)
    builder.writeTimeout(30, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        val logging = CustomLoggingInterceptor()
        logging.setLevel(CustomLoggingInterceptor.Level.BODY)
        builder.addInterceptor(logging)
    }
    return builder.build()
}

fun provideRestApi(okHttpClient: OkHttpClient, gson: Gson): AppRestApi {
    val retrofit = configureRetrofit(Config.Endpoints.BASE_URL, okHttpClient, gson)
    return retrofit.create(AppRestApi::class.java)
}