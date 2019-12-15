package com.example.githubbrowser.util

import org.junit.Assert
import org.junit.Test

class LoadingStateTest {

    private val error = Throwable("Error")
    private val data = listOf("Data1", "Data2")

    @Test
    fun testSealedClass() {
        val succeed = doSuccess(true)
        val fail = doSuccess(false)

        when (succeed) {
            is LoadingState.Success -> {
                Assert.assertEquals("Data1", succeed.value[0])
                Assert.assertEquals("Data2", succeed.value[1])
            }
            is LoadingState.Error -> succeed.exception
        }

        when (fail) {
            is LoadingState.Success -> fail.value
            is LoadingState.Error -> {
                Assert.assertEquals("Error", fail.exception.message)
            }
        }
    }

    private fun doSuccess(isSucceed: Boolean): LoadingState<List<String>> {
        return if (isSucceed) LoadingState.Success(data)
        else LoadingState.Error(error)
    }
}