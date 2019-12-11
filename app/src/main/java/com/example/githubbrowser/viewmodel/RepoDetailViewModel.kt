package com.example.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.model.Commit
import com.example.githubbrowser.model.User
import com.example.githubbrowser.repository.RepoRepository
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.launch

class RepoDetailViewModel : ViewModel() {

    private val repository = RepoRepository

    private val _commitList = MutableLiveData<List<Commit>>()
    val commitList: LiveData<List<Commit>> = _commitList
    private val _loadCommitError = MutableLiveData<Throwable>()
    val loadCommitError: LiveData<Throwable> = _loadCommitError

    private val _collaboratorList = MutableLiveData<List<User>>()
    val collaboratorList: LiveData<List<User>> = _collaboratorList
    private val _loadCollaboratorError = MutableLiveData<Throwable>()
    val loadCollaboratorError: LiveData<Throwable> = _loadCollaboratorError

    fun loadAllCommits(author: String, repoName: String) {
        viewModelScope.launch {
            repository.loadCommits(author, repoName).observeForever {
                when (it) {
                    is LoadingState.Success -> _commitList.postValue(it.value)
                    is LoadingState.Error -> _loadCommitError.postValue(it.exception)
                }
            }
        }
    }

    fun loadAllCollaborators(author: String, repoName: String) {
        viewModelScope.launch {
            repository.loadCollaborators(author, repoName).observeForever {
                when (it) {
                    is LoadingState.Success -> _collaboratorList.postValue(it.value)
                    is LoadingState.Error -> _loadCollaboratorError.postValue(it.exception)
                }
            }
        }
    }

}
