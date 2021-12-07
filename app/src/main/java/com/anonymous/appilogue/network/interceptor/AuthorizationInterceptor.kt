package com.anonymous.appilogue.network.interceptor

import com.anonymous.appilogue.preference.AppilogueSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val preference: AppilogueSharedPreferences
) : Interceptor {
    companion object {
        private const val ACCESS_TOKEN = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                runBlocking(Dispatchers.IO) {
                    val accessToken = preference.getAccessToken() ?: return@runBlocking
                    header(ACCESS_TOKEN, "Bearer $accessToken")
                }
            }
            .build()
    )
}