package com.anonymous.appilogue.di

import android.content.Context
import com.anonymous.appilogue.repository.AppRepository
import com.anonymous.appilogue.repository.FakeAppRepository
import com.anonymous.appilogue.persistence.InstalledAppDao
import com.anonymous.appilogue.repository.SearchAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchAppRepository(
        installedAppDao: InstalledAppDao
    ) = SearchAppRepository(installedAppDao)

    @Singleton
    @Provides
    fun provideAppRepository(): AppRepository = FakeAppRepository()
}