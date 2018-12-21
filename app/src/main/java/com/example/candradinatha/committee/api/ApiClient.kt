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
        const val baseUrl: String = "http://sipantek.gifuto.id/index.php/"

//        10.0.2.2
//        172.17.100.2
        var retrofit: Retrofit? = null

        val c = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val preferences = App.getInstance().getSharedPreferences("login", Context.MODE_PRIVATE)
                    val apiToken = preferences.getString("api_token", null)
                    val adminToken = preferences.getString("admin_token", null)
                    var token: String = ""
                    if (preferences.contains("api_token")){
                        token = apiToken!!
                    } else if (preferences.contains("admin_token")){
                        token = adminToken!!
                    }

                    val request=chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "${token}")
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