package com.example.candradinatha.committee.api

import com.example.candradinatha.committee.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email:String, @Field("password") password:String): Call<LoginResponse>
}