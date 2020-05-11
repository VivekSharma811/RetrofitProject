package com.devtides.retrofitproject.model

import com.devtides.retrofitproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

object ApiCallService {

    private val BASE_URL = "https://us-central1-apis2-e78c3.cloudfunctions.net/"

    val okhttp3Client = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        if(BuildConfig.DEBUG) {
            okhttp3Client.addInterceptor(logging)
        }
    }

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp3Client.build())
        .build()
        .create(ApiCall::class.java)

    private fun getApiRx() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiCall::class.java)

    fun callRx() = getApiRx()

    fun call() =
//        api.callGet()
        //api.callQuery(hashMapOf(Pair("name", "James"), Pair("age", "25"), Pair("city", "Brooklyn")))
//        api.callPost(User("John", "password"))
//        api.callFormData("James", "Bond")
//        api.callFormData(hashMapOf(Pair("firstName", "James"), Pair("lastName", "Bond"), Pair("City", "Brooklyn")))
        api.callHeader(hashMapOf(Pair("user_name", "John"), Pair("last_name", "Winchester"), Pair("TV", "Supernatural")))
}