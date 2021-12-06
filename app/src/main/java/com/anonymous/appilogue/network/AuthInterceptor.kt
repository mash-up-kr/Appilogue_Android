package com.anonymous.appilogue.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            // test 용 token
            addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RAZ21haWwuY29tIiwic3ViIjoxLCJpYXQiOjE2Mzg3MTk5OTgsImV4cCI6MTY3MDI1NTk5OH0.ggp51WjIMA1pS1wK6k5wTxzysFM5SyRhyTLhm9KriUY")
        }.build()

        val response = chain.proceed(newRequest)
        Timber.d("$response")
        return response
    }
}