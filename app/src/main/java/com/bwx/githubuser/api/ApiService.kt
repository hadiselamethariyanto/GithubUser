package com.bwx.githubuser.api

import com.bwx.githubuser.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getListUsers(): Call<List<UserRespons>>

    @GET("users/{login}")
    fun getDetailUsers(@Path("login") login: String): Call<DetailUserResponse>

    @GET("search/users")
    fun searchUser(@Query("q") q: String): Call<SearchResponse>

    @GET("users/{login}/followers")
    fun getFollower(@Path("login") login: String): Call<List<FollowerResponse>>

    @GET("users/{login}/following")
    fun getFollowing(@Path("login") login: String): Call<List<FollowingResponse>>

}