package com.anonymous.appilogue.di

import android.content.Context
import com.anonymous.appilogue.features.search.AppSearchManager
import com.anonymous.appilogue.repository.SearchAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApplicationModule {

    @Singleton
    @Provides
    fun provideAppSearchManager(
        @ApplicationContext context: Context,
        searchAppRepository: SearchAppRepository): AppSearchManager =
        AppSearchManager(context, searchAppRepository)
}