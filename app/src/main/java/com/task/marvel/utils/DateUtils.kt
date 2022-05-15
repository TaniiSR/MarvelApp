package com.task.marvel.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getCurrentTimeStamp(): String {
        val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        val currentLocalTime: Date = cal.time
        val date: DateFormat = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")
        date.timeZone = TimeZone.getTimeZone("GMT")
        return date.format(currentLocalTime)
    }

}