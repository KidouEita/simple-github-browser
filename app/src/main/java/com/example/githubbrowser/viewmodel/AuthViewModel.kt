package com.example.githubbrowser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubbrowser.entity.LoginData
import com.example.githubbrowser.repository.AuthRepository
import com.example.githubbrowser.util.LoadingState
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository

    private val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _userData = MutableLiveData<LoginData>()
    val userData: LiveData<LoginData> = _userData

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    init {
        _isLogin.postValue(false)
        viewModelScope.launch {
            repository.getLoggedData()?.run {
                _isLogin.postValue(true)
                _userData.postValue(this)
            } ?: run {
                _isLogin.postValue(false)
            }
        }
    }

    fun login(code: String) {
        viewModelScope.launch {
            // Fetch
            Log.d("Auth", "From Net")
            repository.getLoginData(code).observeForever {
                when (it) {
                    is LoadingState.Success -> {
                        _userData.postValue(it.value)
                        _isLogin.postValue(true)
                    }
                    is LoadingState.Error -> {
                        _error.postValue(it.exception)
                    }
                }
            }
        }
    }

    fun checkLogged() {
        viewModelScope.launch {
            repository.getLoggedData()?.run {
                Log.d("Auth", "From DB : Name:$name")
                // From DB
                _userData.postValue(this)
                _isLogin.postValue(true)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.clearUserData()
            _isLogin.postValue(false)
        }
    }

}