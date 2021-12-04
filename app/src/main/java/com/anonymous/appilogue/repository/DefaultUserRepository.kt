package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.dto.MyInformationDto
import com.anonymous.appilogue.network.api.AuthApi
import timber.log.Timber
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(private val authApi: AuthApi) :
    UserRepository {

    override suspend fun fetchMyInformation(): MyInformationDto? {
        try {
            val response = authApi.fetchMyInformation()
            Timber.d(response.toString())
            response.body()?.let {
                return it
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
        return null
    }
}
