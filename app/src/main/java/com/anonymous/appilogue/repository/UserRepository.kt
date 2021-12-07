package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.User

interface UserRepository {
    suspend fun fetchMyInformation(): User?
    suspend fun saveMyInformation(user: User): Boolean
}