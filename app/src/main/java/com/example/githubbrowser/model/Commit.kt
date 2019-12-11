package com.example.githubbrowser.model

import com.squareup.moshi.Json

data class Commit(
    val author: User?,
    @Json(name = "commit") val content: CommitNode
) {
    fun getMessage() = content.message
    fun getDate() =
        content.detail.date
            .replace("-", "/")
            .replace("T", " ")
            .replace("Z", "")
}

data class CommitNode(
    val message: String,
    @Json(name = "author") val detail: Detail
)

data class Detail(val date: String)