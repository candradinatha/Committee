package com.example.candradinatha.committee.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun toGMTFormat(date: String?): Date? {
    val formatter = SimpleDateFormat("yyyy-mm-dd")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date"
    return formatter.parse(dateTime)
}

@SuppressLint("SimpleDateFormat")
fun showIndonesianDateTime(dateAndTime: Date?,
                           pattern: String) : String {
    val dateStr: String?
    val locale = Locale("in", "ID")
    val formatter = SimpleDateFormat(pattern, locale)

    dateStr = formatter.format(dateAndTime)
    return dateStr
}