package com.anonymous.appilogue.network.interceptor

import com.anonymous.appilogue.persistence.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                runBlocking(Dispatchers.IO) {
                    val accessToken = PreferencesManager.getToken()
                    header(AUTHORIZATION_KEY, "Bearer $accessToken")
                }
            }
            .build()
    )

    companion object {
        private const val AUTHORIZATION_KEY = "Authorization"
    }
}