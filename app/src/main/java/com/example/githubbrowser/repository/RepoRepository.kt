package com.example.githubbrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubbrowser.api.GithubApiService
import com.example.githubbrowser.api.arg.SearchResponse
import com.example.githubbrowser.model.Commit
import com.example.githubbrowser.model.Repo
import com.example.githubbrowser.model.User
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RepoRepository {

    private val apiService = GithubApiService.apiService

    // Repos
    suspend fun loadPublicRepoList(): LiveData<LoadingState<List<Repo>>> =
        loadRepoList { apiService.getPublicRepos() }

    suspend fun loadUserRepos(name: String? = null): LiveData<LoadingState<List<Repo>>> =
        loadRepoList {
            if (name != null) apiService.getUserRepos(name)
            else apiService.getLoginUserRepos()
        }

    private suspend fun loadRepoList(apiData: suspend () -> List<Repo>): LiveData<LoadingState<List<Repo>>> =
        withContext(Dispatchers.IO) {
            val result = MutableLiveData<LoadingState<List<Repo>>>()
            var data: List<Repo>? = null

            try {
                data = apiData()
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            data?.run { result.postValue(LoadingState.Success(this)) }
            result
        }

    // Commits
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
            data?.run { result.postValue(LoadingState.Success(this)) }
            result
        }

    // Collaborators
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

    // Repos and count
    suspend fun search(queryString: String): LiveData<LoadingState<List<Repo>>> =
        withContext(Dispatchers.IO) {
            val result = MutableLiveData<LoadingState<List<Repo>>>()
            var data: SearchResponse? = null

            try {
                data = apiService.searchRepositories(queryString)
            } catch (e: Throwable) {
                result.postValue(LoadingState.Error(e))
            }
            data?.run { result.postValue(LoadingState.Success(repos)) }
            result
        }
}