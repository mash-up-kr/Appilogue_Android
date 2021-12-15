package com.anonymous.appilogue.utils

import android.content.Context
import com.anonymous.appilogue.R
import java.text.SimpleDateFormat
import java.util.*

fun Date.dateToPassedTime(context: Context): String {
    var passedTime = (Date(System.currentTimeMillis()).time - this.time) / MILLISECOND
    val timeFormatters = listOf(
        MAX_SECOND to R.string.history_second_passed,
        MAX_MINUTE to R.string.history_minute_passed,
        MAX_HOUR to R.string.history_hour_passed,
        MAX_DAY to R.string.history_day_passed,
        MAX_WEEK to R.string.history_week_passed,
        MAX_MONTH to R.string.history_month_passed,
    )
    timeFormatters.forEach { formatter ->
        if (passedTime < formatter.first) {
            return context.getString(formatter.second, passedTime.coerceAtLeast(0))
        }
        passedTime /= formatter.first
    }

    return try {
        val dateFormat = context.getString(R.string.history_year_format)
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        simpleDateFormat.format(this)
    } catch (e: Exception) {
        ""
    }
}

const val MILLISECOND = 1000
const val MAX_SECOND = 60
const val MAX_MINUTE = 60
const val MAX_HOUR = 24
const val MAX_DAY = 7
const val MAX_WEEK = 4
const val MAX_MONTH = 11
const val DAY_MILLISECOND = MILLISECOND * MAX_SECOND * MAX_MINUTE * MAX_HOUR * 1L
const val WEEK_MILLISECOND = DAY_MILLISECOND * 7L
const val MONTH_MILLISECOND = DAY_MILLISECOND * 30L