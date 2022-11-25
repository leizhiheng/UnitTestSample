package com.ubt.unittestsample.robolectric.net

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubService {
    @GET("users/{username}/repos")
    fun publicRepositories(@Path("username") username: String): Call<List<Repository>>

    @GET("users/{username}/following")
    fun followingUser(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}")
    fun user(@Path("username") username: String): Call<User>

    object Factory {
        fun create(): GithubService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubService::class.java)
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }
}