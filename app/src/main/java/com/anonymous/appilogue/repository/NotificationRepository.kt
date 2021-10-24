package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.Notification


interface NotificationRepository {
    fun getNotification(): List<Notification>
}