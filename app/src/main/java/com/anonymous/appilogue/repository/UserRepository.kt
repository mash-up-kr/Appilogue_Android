package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.ReviewedApp
import com.anonymous.appilogue.model.dto.MyInformationDto

interface UserRepository {
    suspend fun fetchMyInformation(): MyInformationDto?
}