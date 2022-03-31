package com.bwx.githubuser.domain.repository

import androidx.paging.PagingData
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.domain.model.Repository
import com.bwx.githubuser.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {
    fun getPagingUsers(): Flow<PagingData<User>>

    fun getDetailUser(login: String): Flow<Resource<User>>

    fun getUserRepository(login: String): Flow<Resource<List<Repository>>>
}