package com.example.githubbrowser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.entity.RepoVO
import com.example.githubbrowser.repository.RepoRepository
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = RepoRepository

    private val _repoList = MutableLiveData<Sequence<RepoVO>>()
    val repoList: LiveData<Sequence<RepoVO>> = _repoList

    // Loading Error
    private val _loadError = MutableLiveData<Throwable>()
    val loadError: LiveData<Throwable> = _loadError

    fun loadAllPublicRepoList() {
        viewModelScope.launch {
            repository.loadPublicRepoList().observeForever{
                when(it) {
                    is LoadingState.Success -> {
//                        Log.d(javaClass.simpleName, it.value[0].title)
                        _repoList.postValue(it.value.asSequence())
                    }
                    is LoadingState.Error -> {
                        _loadError.postValue(it.exception)
                    }
                }
            }
        }
    }

}