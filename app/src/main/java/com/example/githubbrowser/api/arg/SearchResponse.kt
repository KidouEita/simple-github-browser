package com.example.githubbrowser.api.arg

import com.example.githubbrowser.model.Repo
import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "total_count") val count: Int,
    @Json(name = "items") val repos: List<Repo>
)