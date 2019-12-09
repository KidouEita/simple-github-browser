package com.example.githubbrowser.api.arg

import com.squareup.moshi.Json

data class TokenHolder(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "token_type") val tokenType: String
)
