package com.anonymous.appilogue.features.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anonymous.appilogue.R
import com.anonymous.appilogue.repository.NotificationRepository
import com.anonymous.appilogue.utils.DAY_MILLISECOND
import com.anonymous.appilogue.utils.MONTH_MILLISECOND
import com.anonymous.appilogue.utils.WEEK_MILLISECOND
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val notificationRepository: NotificationRepository) :
    ViewModel() {
    private val _notifications = MutableLiveData<List<NotificationListItem>>()
    val notifications: LiveData<List<NotificationListItem>> = _notifications

    fun fetchNotifications() {
        val notifications = notificationRepository.getNotification()
        _notifications.value = TimeByNotification
            .values()
            .sortedWith(compareBy { it.order })
            .map {
                listOf(NotificationListItem.Header(it.stringId)) +
                        notifications
                            .filter { notification -> notification.date.splitByNotification() == it }
                            .map {
                                NotificationListItem.NotificationItem(it)
                            }
            }
            .flatten()
    }
}

fun Date.splitByNotification(): TimeByNotification? {
    val passedTime = Date(System.currentTimeMillis()).time - this.time
    return TimeByNotification.values().find { it.millisecondRange.contains(passedTime) }
}

enum class TimeByNotification(val stringId: Int, val millisecondRange: LongRange, val order: Int) {
    TODAY(R.string.today, 1 until DAY_MILLISECOND, 1),
    THIS_WEEK(R.string.this_week, DAY_MILLISECOND until WEEK_MILLISECOND, 2),
    THIS_MONTH(R.string.this_month, WEEK_MILLISECOND until MONTH_MILLISECOND, 3),
    DID_BEFORE(R.string.did_before, MONTH_MILLISECOND until Long.MAX_VALUE, 4)
}