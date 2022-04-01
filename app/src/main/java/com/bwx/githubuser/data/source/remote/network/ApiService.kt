package com.bwx.githubuser.data.source.remote.network

import com.bwx.githubuser.data.source.remote.response.*
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun getPagingUsers(
        @Query("per_page") per_page: Int,
        @Query("since") since: Int? = null
    ): List<UserResponse>

    @GET("users/{login}")
    suspend fun getDetailUsers(@Path("login") login: String): DetailUserResponse

    @GET("users/{login}/repos")
    suspend fun getUserRepository(@Path("login") login: String): List<RepositoryResponse>

    @GET("users/{login}/following")
    suspend fun getUserFollowing(@Path("login") login: String): List<FollowingResponse>

    @GET("users/{login}/followers")
    suspend fun getUserFollowers(@Path("login") login: String): List<FollowersResponse>

}