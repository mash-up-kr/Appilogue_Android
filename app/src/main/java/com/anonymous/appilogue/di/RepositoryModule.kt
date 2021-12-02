package com.anonymous.appilogue.di

import com.anonymous.appilogue.network.api.ItemApi
import com.anonymous.appilogue.network.api.SearchApi
import com.anonymous.appilogue.persistence.InstalledAppDao
import com.anonymous.appilogue.repository.*
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

    @Singleton
    @Provides
    fun provideAppRepository(): AppRepository = FakeAppRepository()

    @Singleton
    @Provides
    fun provideReviewRepository(searchApi: SearchApi): ReviewRepository =
        FakeReviewRepository(searchApi)

    @Singleton
    @Provides
    fun provideItemRepository(itemApi: ItemApi): ItemRepository =
        DefaultItemRepository(itemApi)

}