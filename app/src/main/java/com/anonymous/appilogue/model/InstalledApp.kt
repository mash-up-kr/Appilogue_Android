package com.anonymous.appilogue.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

data class InstalledApp(
    val name: String,
    val icon: Bitmap
)

@Entity
data class InstalledAppEntity(
    @PrimaryKey val name: String,
    val icon: Bitmap
)

fun InstalledApp.toEntity() = InstalledAppEntity(
    name = name,
    icon = icon
)

fun InstalledAppEntity.toData() = InstalledApp(
    name = name,
    icon = icon
)