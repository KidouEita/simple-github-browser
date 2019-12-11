package com.example.githubbrowser.model

import com.squareup.moshi.Json

data class Repo(
    val id: Int,
    @Json(name = "name") val title: String,
    val owner: User
)