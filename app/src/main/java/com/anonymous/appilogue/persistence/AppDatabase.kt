package com.anonymous.appilogue.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anonymous.appilogue.model.InstalledAppEntity
import com.anonymous.appilogue.persistence.converter.BitmapTypeConverter


@Database(entities = [InstalledAppEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [BitmapTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun installedAppDao(): InstalledAppDao
}
