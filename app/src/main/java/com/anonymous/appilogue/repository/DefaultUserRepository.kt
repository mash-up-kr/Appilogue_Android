package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.User
import com.anonymous.appilogue.model.dto.MyInformationDto
import com.anonymous.appilogue.model.dto.UpdateUserDto
import com.anonymous.appilogue.network.api.AuthApi
import com.anonymous.appilogue.network.api.UserApi
import timber.log.Timber
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val authApi: AuthApi,
    private val userApi: UserApi
) :
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

    override suspend fun saveMyInformation(user: User): Boolean {
        try {
            val response =
                userApi.updateUser(UpdateUserDto(null, user.nickname, null, user.profileImage))
            Timber.d(response.toString())
            if(response.isSuccessful){
                return true
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
        return false
    }
}
