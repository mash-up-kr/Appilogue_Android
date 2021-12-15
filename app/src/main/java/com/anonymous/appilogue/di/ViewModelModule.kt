package com.anonymous.appilogue.di

import com.anonymous.appilogue.features.community.reviewlist.ReviewListDataProvider
import com.anonymous.appilogue.usecase.FetchReviewListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun providePageDataProvider(fetchReviewListUseCase: FetchReviewListUseCase): ReviewListDataProvider =
        ReviewListDataProvider(fetchReviewListUseCase)
}