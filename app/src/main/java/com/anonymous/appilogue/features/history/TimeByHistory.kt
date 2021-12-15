package com.anonymous.appilogue.features.history

import com.anonymous.appilogue.R
import com.anonymous.appilogue.utils.DAY_MILLISECOND
import com.anonymous.appilogue.utils.MONTH_MILLISECOND
import com.anonymous.appilogue.utils.WEEK_MILLISECOND
import java.util.*

enum class TimeByHistory(val stringId: Int, val millisecondRange: LongRange, val order: Int) {
    TODAY(R.string.today, 1 until DAY_MILLISECOND, 1),
    THIS_WEEK(R.string.this_week, DAY_MILLISECOND until WEEK_MILLISECOND, 2),
    THIS_MONTH(R.string.this_month, WEEK_MILLISECOND until MONTH_MILLISECOND, 3),
    DID_BEFORE(R.string.did_before, MONTH_MILLISECOND until Long.MAX_VALUE, 4)
}

fun Date.splitByHistory(): TimeByHistory? {
    val passedTime = Date(System.currentTimeMillis()).time - this.time
    return TimeByHistory.values().find { it.millisecondRange.contains(passedTime) }
}