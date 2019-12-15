package com.example.githubbrowser.util

sealed class LoadingState<T> {
    data class Success<T>(val value: T) : LoadingState<T>()
    data class Error<T>(val exception: Throwable) : LoadingState<T>()
}