package com.example.githubbrowser.util

sealed class LoadingState<T> {
    data class Success<T>(val value: T) : LoadingState<T>()
    data class Error<T>(val exception: Throwable) : LoadingState<T>()
}

// TODO
sealed class UploadState<T> {
    data class Success<T>(val value: T) : UploadState<T>()
    data class Failed<T>(val exception: Throwable) : UploadState<T>()
}

sealed class UploadStateWithNoResponse {
    object Success : UploadStateWithNoResponse()
    data class Error(val exception: Throwable) : UploadStateWithNoResponse()
}