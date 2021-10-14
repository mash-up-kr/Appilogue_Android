package com.anonymous.appilogue.persistence

import androidx.room.RoomDatabase


abstract class AppDatabase : RoomDatabase() {
    abstract fun installedAppDao(): InstalledAppDao
}
