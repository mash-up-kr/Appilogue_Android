package com.anonymous.appilogue.di

import com.anonymous.appilogue.repository.FakeHistoryRepository
import com.anonymous.appilogue.repository.HistoryRepository
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

    @Provides
    @Singleton
    fun provideHistoryRepository(): HistoryRepository = FakeHistoryRepository()
}