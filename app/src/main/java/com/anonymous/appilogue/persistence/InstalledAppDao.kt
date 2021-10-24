package com.anonymous.appilogue.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anonymous.appilogue.model.InstalledAppEntity

@Dao
interface InstalledAppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInstalledAppList(installedAppLis: List<InstalledAppEntity>)

    @Query("SELECT * FROM InstalledAppEntity")
    suspend fun fetchInstalledAppList(): List<InstalledAppEntity>
}