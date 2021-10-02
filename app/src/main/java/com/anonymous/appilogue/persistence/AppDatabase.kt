package com.anonymous.appilogue.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anonymous.appilogue.model.InstalledApp

abstract class AppDatabase : RoomDatabase() {
    abstract fun installedAppDao(): InstalledAppDao
}