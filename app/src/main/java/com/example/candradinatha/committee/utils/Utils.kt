package com.example.candradinatha.committee.utils

import android.content.Context
import android.net.ConnectivityManager

fun isNetworkAvailable(ctx: Context?): Boolean {
    val connectivityManager = ctx?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else {
        false
    }
}