package com.anonymous.appilogue.features.base

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Failure(val throwable: Throwable) : UiState<Nothing>()
}

val UiState<*>.isSuccessful
    get() = this is UiState.Success && data != null

fun <T> UiState<T>.successOr(fallback: T): T {
    return (this as? UiState.Success<T>)?.data ?: fallback
}

fun <T> UiState<T>.throwableOrNull(): Throwable? {
    return (this as? UiState.Failure)?.throwable
}

fun <T> UiState<T>.resultMessage(onSuccessMessage: String, onFailureMessage: String): String {
    return if (isSuccessful) onSuccessMessage else "$onFailureMessage - ${throwableOrNull()}"
}