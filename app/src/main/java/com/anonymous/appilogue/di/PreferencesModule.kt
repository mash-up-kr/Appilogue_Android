package com.anonymous.appilogue.di

import android.content.SharedPreferences
import com.anonymous.appilogue.preference.AppilogueSharedPreferences
import com.anonymous.appilogue.preference.DefaultAppilogueSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreferencesModule {

    @Singleton
    @Provides
    fun provideAppilogueSharedPreferences(sharedPreferences: SharedPreferences):
            AppilogueSharedPreferences =
        DefaultAppilogueSharedPreferences(sharedPreferences)
}