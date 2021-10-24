package com.anonymous.appilogue.di

import com.anonymous.appilogue.utils.AppCoroutineDispatchers
import com.anonymous.appilogue.utils.AppRxJavaDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DispatcherModule {

    @Singleton
    @Provides
    fun provideAppCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        main = Dispatchers.Main,
        io = Dispatchers.IO,
        computation = Dispatchers.Default
    )

    @Singleton
    @Provides
    fun provideRxJavaDispatchers(): AppRxJavaDispatchers = AppRxJavaDispatchers(
        main = AndroidSchedulers.mainThread(),
        io = Schedulers.io(),
        computation = Schedulers.computation()
    )
}