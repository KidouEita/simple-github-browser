package com.example.githubbrowser.api

import com.example.githubbrowser.model.LoginData

class TokenHolder {

    companion object {
        var data: LoginData? = null
        val token
            get() = "Bearer ${data?.token}"
    }

}