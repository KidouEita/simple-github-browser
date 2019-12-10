package com.example.githubbrowser.api.arg

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: Int,
    @Json(name = "login") val name: String,
    @Json(name = "avatar_url") val avatarUrl: String
)