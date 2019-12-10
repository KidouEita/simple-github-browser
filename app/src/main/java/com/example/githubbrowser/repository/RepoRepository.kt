package com.example.githubbrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubbrowser.api.GithubApiService
import com.example.githubbrowser.api.arg.Repo
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RepoRepository {

    private val apiService = GithubApiService.apiService

    suspend fun loadPublicRepoList(): LiveData<LoadingState<List<Repo>>> =
        withContext(Dispatchers.IO) {

            val result = MutableLiveData<LoadingState<List<Repo>>>()
            var data: List<Repo>? = null

            try {
                data = apiService.getPublicRepos()
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            data?.run {
                result.postValue(LoadingState.Success(this))
            }

            result
        }
}