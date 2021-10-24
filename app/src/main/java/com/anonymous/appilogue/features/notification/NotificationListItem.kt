package com.anonymous.appilogue.features.notification

import com.anonymous.appilogue.model.Notification

sealed class NotificationListItem {
    abstract val id: Int

    data class NotificationItem(val notification: Notification) : NotificationListItem() {
        override val id = notification.id
    }

    class Header(val title: String) : NotificationListItem() {
        override val id = Int.MIN_VALUE
    }
}