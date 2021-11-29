package com.anonymous.appilogue.di

import com.anonymous.appilogue.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LoginModule {
    @Singleton
    @Provides
    fun provideRepository(): LoginRepository = LoginRepository()
}
