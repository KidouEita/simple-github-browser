package com.example.githubbrowser.api

import com.example.githubbrowser.BuildConfig
import com.example.githubbrowser.api.arg.Repo
import com.example.githubbrowser.api.arg.TokenHolder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface GithubApiService {

    companion object {

        val loginService by lazy { create(BuildConfig.LOGIN_URL) }
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
    * Login
    * */

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun login(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): TokenHolder

    /*
    * API
    * */

    @GET("repositories")
    suspend fun getPublicRepos(): List<Repo>

    @GET("repos/{author}/{repo}/contributors")
    suspend fun getAllContributors()

    @GET("users/{user}/repos")
    suspend fun getOwnRepos()

}