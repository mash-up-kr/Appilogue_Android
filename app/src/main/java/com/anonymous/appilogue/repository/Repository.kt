package com.anonymous.appilogue.repository

import com.anonymous.appilogue.exceptions.ClientErrorException
import com.anonymous.appilogue.exceptions.ServerErrorException
import com.anonymous.appilogue.exceptions.UnknownException
import retrofit2.Response

interface Repository

internal fun <T> Repository.handleApiError(response: Response<T>): Nothing {
    when (response.code()) {
        in 400..499 -> throw ClientErrorException(
            response.code(),
            response.raw().message()
        )
        in 500..599 -> throw ServerErrorException(
            response.code(),
            response.raw().message()
        )
        else -> throw UnknownException(response.code(), response.raw().message())
    }
}