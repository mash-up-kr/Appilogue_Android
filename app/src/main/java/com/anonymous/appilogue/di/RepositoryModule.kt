package com.anonymous.appilogue.di

import android.content.Context
import com.anonymous.appilogue.repository.SearchAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideSearchAppRepository(
        @ApplicationContext context: Context
    ) = SearchAppRepository(context)
}