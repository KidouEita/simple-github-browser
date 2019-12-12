package com.example.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.model.Repo
import com.example.githubbrowser.repository.RepoRepository
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.launch

class AuthorViewModel : ViewModel() {

    private val repository = RepoRepository

    private val _repoList = MutableLiveData<List<Repo>>()
    val repoList: LiveData<List<Repo>> = _repoList

    // Loading Error
    private val _loadError = MutableLiveData<Throwable>()
    val loadError: LiveData<Throwable> = _loadError

    fun loadRepos(name: String? = null) {
        viewModelScope.launch {
            repository.loadUserRepos(name).observeForever {
                when (it) {
                    is LoadingState.Success -> _repoList.postValue(it.value)
                    is LoadingState.Error -> _loadError.postValue(it.exception)
                }
            }
        }
    }
}
