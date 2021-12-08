package com.anonymous.appilogue.di

import com.anonymous.appilogue.network.api.SearchApi
import com.anonymous.appilogue.network.api.*
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
        installedAppDao: InstalledAppDao,
        appApi: AppApi
    ) = SearchAppRepository(installedAppDao, appApi)

    @Singleton
    @Provides
    fun provideReviewRepository(
        searchApi: SearchApi,
        reviewApi: ReviewApi,
        commentApi: CommentApi,
        imageApi: ImageApi
    ): ReviewRepository = ReviewRepository(searchApi, reviewApi, commentApi, imageApi)

    @Singleton
    @Provides
    fun provideReportRepository(
        reportApi: ReportApi
    ): ReportRepository = ReportRepository(reportApi)

    @Singleton
    @Provides
    fun provideLoginRepository(authApi: AuthApi): LoginRepository = LoginRepository(authApi)

    @Singleton
    @Provides
    fun provideItemRepository(itemApi: ItemApi): ItemRepository =
        DefaultItemRepository(itemApi)

    @Singleton
    @Provides
    fun provideUserRepository(authApi: AuthApi, userApi: UserApi): UserRepository =
        DefaultUserRepository(authApi, userApi)

}
