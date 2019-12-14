package com.example.githubbrowser.api

import com.example.githubbrowser.BuildConfig
import com.example.githubbrowser.api.arg.SearchResponse
import com.example.githubbrowser.model.Commit
import com.example.githubbrowser.model.Repo
import com.example.githubbrowser.model.Token
import com.example.githubbrowser.model.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface GithubApiService {

    companion object {

        val authService by lazy { create(BuildConfig.LOGIN_URL) }
        val apiService by lazy { create(BuildConfig.API_URL) }

        private fun create(url: String): GithubApiService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(url)
                .build()
            return retrofit.create(GithubApiService::class.java)
        }
    }

    /*
    * Auth
    * */

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun login(
        @Field("client_id") clientId: String = BuildConfig.GITHUB_CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.GITHUB_CLIENT_SECRET,
        @Field("code") code: String
    ): Token

    /*
    * API
    * */

    // For Logged In
    @GET("user")
    suspend fun getLoginUserData(@Header("Authorization") authorization: String = TokenHolder.token): User

    // Repos
    @GET("user/repos")
    suspend fun getLoginUserRepos(@Header("Authorization") authorization: String = TokenHolder.token): List<Repo>
    @GET("repositories")
    suspend fun getPublicRepos(): List<Repo>
    @GET("users/{user}/repos")
    suspend fun getUserRepos(@Path("user") user: String): List<Repo>

    @GET("repos/{author}/{repo}/commits")
    suspend fun getAllCommits(
        @Header("Authorization") authorization: String = TokenHolder.token,
        @Path("author") author: String,
        @Path("repo") repo: String
    ): List<Commit>

    @GET("repos/{author}/{repo}/collaborators")
    suspend fun getAllCollaborators(
        @Header("Authorization") authorization: String = TokenHolder.token,
        @Path("author") author: String,
        @Path("repo") repo: String
    ): List<User>

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") searchText: String
    ): SearchResponse
}
