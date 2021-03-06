package com.bwx.githubuser.data.source.remote

import com.bwx.githubuser.data.source.remote.network.ApiResponse
import com.bwx.githubuser.data.source.remote.network.ApiService
import com.bwx.githubuser.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getPagingUsers(per_page: Int, since: Int?): List<UserResponse> =
        apiService.getPagingUsers(per_page = per_page, since = since)

    suspend fun getDetailUser(login: String): Flow<ApiResponse<DetailUserResponse>> {
        return flow {
            try {
                val response = apiService.getDetailUsers(login)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserRepository(login: String): Flow<ApiResponse<List<RepositoryResponse>>> {
        return flow {
            try {
                val response = apiService.getUserRepository(login)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowing(login: String): Flow<ApiResponse<List<FollowingResponse>>> {
        return flow {
            try {
                val response = apiService.getUserFollowing(login)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowers(login: String): Flow<ApiResponse<List<FollowersResponse>>> {
        return flow {
            try {
                val response = apiService.getUserFollowers(login)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}