package com.example.githubbrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubbrowser.api.GithubApiService
import com.example.githubbrowser.model.Commit
import com.example.githubbrowser.model.Repo
import com.example.githubbrowser.model.User
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

    suspend fun loadCommits(
        author: String,
        repoName: String
    ): LiveData<LoadingState<List<Commit>>> =
        withContext(Dispatchers.IO) {

            val result = MutableLiveData<LoadingState<List<Commit>>>()
            var data: List<Commit>? = null

            try {
                data = apiService.getAllCommits(author = author, repo = repoName)
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            data?.run {
                result.postValue(LoadingState.Success(this))
            }

            result
        }

    suspend fun loadCollaborators(
        author: String,
        repoName: String
    ): LiveData<LoadingState<List<User>>> =
        withContext(Dispatchers.IO) {

            val result = MutableLiveData<LoadingState<List<User>>>()
            var data: List<User>? = null

            try {
                data = apiService.getAllCollaborators(author = author, repo = repoName)
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            data?.run {
                result.postValue(LoadingState.Success(this))
            }

            result
        }

    suspend fun loadUserRepos(name: String? = null): LiveData<LoadingState<List<Repo>>> =
        withContext(Dispatchers.IO) {

            val result = MutableLiveData<LoadingState<List<Repo>>>()
            var data: List<Repo>? = null

            try {
                data = if (name != null) apiService.getUserRepos(name)
                else apiService.getLoginUserRepos()
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            data?.run {
                result.postValue(LoadingState.Success(this))
            }

            result
        }
}