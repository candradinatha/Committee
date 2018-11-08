package com.example.candradinatha.committee.api

import android.content.Context
import com.example.candradinatha.committee.App
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object {
        const val baseUrl: String = "http://172.17.100.2:8000/api/"
        var retrofit: Retrofit? = null

        val c = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
                    val apiToken = preferences.getString("api_token", null)
                    val request=chain.request()
                            .newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", "Bearer $apiToken")
                            .build()

                    chain.proceed(request)
                }.build()
        val client: Retrofit?
                get() {
                    if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(c)
                                .build()
                    }
                    return  retrofit
                }
    }
}