package com.anonymous.appilogue.utils

import io.reactivex.rxjava3.core.Scheduler
import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher
)

data class AppRxJavaDispatchers(
    val main: Scheduler,
    val io: Scheduler,
    val computation: Scheduler
)