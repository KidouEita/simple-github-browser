package com.example.githubbrowser.model

import com.squareup.moshi.Json

data class Token(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type") val tokenType: String
)
