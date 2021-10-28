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
            .map { timeByNotification ->
                listOf(NotificationListItem.Header(timeByNotification.stringId)) +
                        notifications
                            .filter { notification -> notification.date.splitByNotification() == timeByNotification }
                            .map { notification ->
                                NotificationListItem.NotificationItem(notification)
                            }
            }
            .flatten()
    }
}