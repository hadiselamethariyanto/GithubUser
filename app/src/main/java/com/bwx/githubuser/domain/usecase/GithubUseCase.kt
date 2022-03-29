package com.bwx.githubuser.domain.usecase

import androidx.paging.PagingData
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.domain.model.User
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {
    fun getPagingUsers(): Flow<PagingData<User>>

    fun getDetailUser(login: String): Flow<Resource<User>>
}