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
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RAZ21haWwuY29tIiwic3ViIjoxLCJpYXQiOjE2Mzg3MTk5OTgsImV4cCI6MTY3MDI1NTk5OH0.ggp51WjIMA1pS1wK6k5wTxzysFM5SyRhyTLhm9KriUY"
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