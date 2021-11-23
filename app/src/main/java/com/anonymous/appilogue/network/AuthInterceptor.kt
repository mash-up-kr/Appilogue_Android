package com.anonymous.appilogue.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            // test 용 token
            addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRlc3RAZ21haWwuY29tIiwic3ViIjoxLCJpYXQiOjE2MzcxNTU0OTksImV4cCI6MTY2ODY5MTQ5OX0.1ne7icFLwgmZR0sxHspte3AHGLRdc9WhFB8ZCu3Jogc")
        }.build()

        return chain.proceed(newRequest)
    }
}