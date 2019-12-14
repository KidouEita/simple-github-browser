package com.example.githubbrowser.api

import com.example.githubbrowser.model.LoginData

class TokenHolder {
    companion object {
        var data: LoginData? = null
        val token: String
            get() = if (data?.token != null) "Bearer ${data?.token}" else ""
    }
}