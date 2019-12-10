package com.example.githubbrowser.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubbrowser.api.GithubApiService
import com.example.githubbrowser.api.TokenHolder
import com.example.githubbrowser.api.arg.Token
import com.example.githubbrowser.api.arg.User
import com.example.githubbrowser.entity.LoginData
import com.example.githubbrowser.storage.GithubBrowserDatabase
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthRepository {

    private val authService = GithubApiService.authService
    private val apiService = GithubApiService.apiService
    private val db = GithubBrowserDatabase.instance
    private var loginData: LoginData? = null

    suspend fun getLoginData(code: String): LiveData<LoadingState<LoginData>> =
        withContext(Dispatchers.IO) {

            val result = MutableLiveData<LoadingState<LoginData>>()
            var user: User? = null
            var token: Token? = null

            try {
                token = authService.login(code = code)
                user = apiService.getUserData("Bearer ${token.accessToken}")
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            user?.run {
                Log.d("AuthRepo", "Token:${token?.accessToken} Type:${token?.tokenType}")
                loginData = LoginData(
                    user.id,
                    user.name,
                    user.avatarUrl,
                    token!!.accessToken
                )
                result.postValue(LoadingState.Success(loginData!!))
                db?.loginDataDao()?.insertLoginData(loginData!!)
            }
            result
        }

    suspend fun clearUserData() =
        withContext(Dispatchers.IO) {
            loginData?.run {
                db?.loginDataDao()?.deleteLoginData(this)
                loginData = null
            }
        }

    suspend fun getLoginData() =
        withContext(Dispatchers.IO) {
            db?.loginDataDao()?.getLoggedInData()
        }
}