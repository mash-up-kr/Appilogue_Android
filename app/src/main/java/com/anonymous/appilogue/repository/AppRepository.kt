package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.ReviewedApp

interface AppRepository {
    fun getBlackHoleApps(): List<ReviewedApp>
    fun getWhiteHoleApps(): List<ReviewedApp>
}