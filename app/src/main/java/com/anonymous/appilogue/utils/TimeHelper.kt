package com.anonymous.appilogue.utils

import android.content.Context
import com.anonymous.appilogue.R
import java.util.*

fun Date.dateToPassedTime(context: Context): String {
    var passedTime = Date(System.currentTimeMillis()).time - this.time
    passedTime /= MILLISECOND
    if (passedTime < MAX_SECOND)
        return context.getString(R.string.notification_second_passed, passedTime) else
        passedTime /= MAX_SECOND
    if (passedTime < MAX_MINUTE)
        return context.getString(R.string.notification_minute_passed, passedTime) else
        passedTime /= MAX_MINUTE
    if (passedTime < MAX_HOUR)
        return context.getString(R.string.notification_hour_passed, passedTime) else
        passedTime /= MAX_HOUR
    if (passedTime < MAX_DAY)
        return context.getString(R.string.notification_day_passed, passedTime) else
        passedTime /= MAX_DAY
    return context.getString(R.string.notification_month_passed, passedTime)
}

const val MILLISECOND = 1000
const val MAX_SECOND = 60
const val MAX_MINUTE = 60
const val MAX_HOUR = 24
const val MAX_DAY = 30