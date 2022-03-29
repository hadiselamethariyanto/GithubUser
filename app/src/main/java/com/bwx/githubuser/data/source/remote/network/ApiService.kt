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

}