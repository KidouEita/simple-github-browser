package com.example.githubbrowser.api.arg

import com.example.githubbrowser.entity.RepoVO
import com.squareup.moshi.Json

data class Repo(
    val id: Int,
    @Json(name = "name") val title: String,
    val owner: Owner
) : RepoVO {
    override fun getRepoName() = title
    override fun getAuthorName() = owner.name
}

data class Owner(
    @Json(name = "login") val name: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "repos_url") val ownRepos: String
)