package com.anonymous.appilogue.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.model.InstalledAppEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InstalledAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledAppList(installedAppLis: List<InstalledAppEntity>)

    @Query("SELECT * FROM InstalledAppEntity")
    fun fetchInstalledAppList(): Flow<List<InstalledAppEntity>>
}