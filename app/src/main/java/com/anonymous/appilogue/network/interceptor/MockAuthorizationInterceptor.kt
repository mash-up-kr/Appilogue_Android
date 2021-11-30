package com.anonymous.appilogue.network.interceptor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class MockAuthorizationInterceptor @Inject constructor() : Interceptor {
    companion object {
        private const val ACCESS_TOKEN = "Authorization"
        private const val MOCK_ACCESS_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RAZ21haWwuY29tIiwic3ViIjoxLCJpYXQiOjE2MzcxNTU0OTksImV4cCI6MTY2ODY5MTQ5OX0.1ne7icFLwgmZR0sxHspte3AHGLRdc9WhFB8ZCu3Jogc"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                runBlocking(Dispatchers.IO) {
                    header(ACCESS_TOKEN, "Bearer $MOCK_ACCESS_TOKEN")
                }
            }
            .build()
    )
}