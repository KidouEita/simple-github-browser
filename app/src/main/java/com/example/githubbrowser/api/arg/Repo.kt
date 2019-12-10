package com.example.githubbrowser.api.arg

import com.example.githubbrowser.vo.RepoVO
import com.squareup.moshi.Json

data class Repo(
    val id: Int,
    @Json(name = "name") val title: String,
    val owner: User
) : RepoVO {
    override fun getRepoName() = title
    override fun getAuthorName() = owner.name
}