package com.example.candradinatha.committee.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object {
        const val baseUrl: String = "http://172.17.100.2:8000/api/"
        var retrofit: Retrofit? = null

        val client: Retrofit?
                get() {
                    if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build()
                    }
                    return  retrofit
                }
    }
}



//object ApiClient{
//    private var retrofit : Retrofit? = null
//
//    fun getClient(baseUrl:String): Retrofit {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//        }
//        return retrofit!!
//    }
//}