package com.task.marvel.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss z"
    fun getCurrentTimeStamp(): String {
        val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        val currentLocalTime: Date = cal.time
        val date: DateFormat = SimpleDateFormat(DATE_FORMAT)
        date.timeZone = TimeZone.getTimeZone("GMT")
        return date.format(currentLocalTime)
    }

    fun getDate(time: String): Date? {
        val format = SimpleDateFormat(DATE_FORMAT)
        try {
            return format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun isAfterDay(saveDate: Date, currentDate: Date): Boolean {
        val cal = Calendar.getInstance()
        cal.time = saveDate
        cal.add(Calendar.DATE, 1)
        return currentDate.after(cal.time)
    }
}