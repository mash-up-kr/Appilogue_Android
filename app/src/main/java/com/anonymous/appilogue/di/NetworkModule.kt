package com.anonymous.appilogue.di

import com.anonymous.appilogue.network.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.moussg.io/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Provides
    @Singleton
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewApi(
        retrofit: Retrofit
    ): ReviewApi {
        return retrofit.create(ReviewApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentApi(
        retrofit: Retrofit
    ): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReportApi(
        retrofit: Retrofit
    ): ReportApi {
        return retrofit.create(ReportApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageApi(
        retrofit: Retrofit
    ): ImageApi {
        return retrofit.create(ImageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppApi(
        retrofit: Retrofit
    ): AppApi {
        return retrofit.create(AppApi::class.java)
    }
}