package com.example.githubbrowser.api

import com.example.githubbrowser.BuildConfig
import com.example.githubbrowser.api.arg.Repo
import com.example.githubbrowser.api.arg.Token
import com.example.githubbrowser.api.arg.User
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

    // TODO
    @GET("user")
    suspend fun getUserData(@Header("Authorization") authorization: String = TokenHolder.token): User

    @GET("repositories")
    suspend fun getPublicRepos(): List<Repo>

    // TODO
    @GET("repos/{author}/{repo}/contributors")
    suspend fun getAllContributors()

    // TODO
    @GET("users/{user}/repos")
    suspend fun getOwnRepos()

}