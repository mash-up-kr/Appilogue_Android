package com.anonymous.appilogue.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anonymous.appilogue.model.InstalledApp

@Dao
interface InstalledAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledAppList(installedAppLis: List<InstalledApp>)

    @Query("SELECT * FROM InstalledApp")
    suspend fun getAllInstalledAppList(): List<InstalledApp>
}