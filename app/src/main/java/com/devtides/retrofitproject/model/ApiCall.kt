package com.devtides.retrofitproject.model

import retrofit2.Call
import retrofit2.http.*

interface ApiCall {

    @GET("apiCall")
    fun callGet() : Call<ApiCallResponse>

    @GET("apiCall")
    fun callQuery(@QueryMap params : Map<String, String>?) : Call<ApiCallResponse>

    @POST("apiCall")
    fun callPost(@Body user : User) : Call<ApiCallResponse>

    @FormUrlEncoded
    @POST("apiCall")
    fun callFormData(@Field("first_name") first_name : String?,
                        @Field("last_name") last_name : String?) : Call<ApiCallResponse>

    @FormUrlEncoded
    @POST("apiCall")
    fun callFormData(@FieldMap field : Map<String, String>?) : Call<ApiCallResponse>

    @GET("apiCall")
    fun callHeader(@HeaderMap headers : Map<String, String>?) : Call<ApiCallResponse>

}