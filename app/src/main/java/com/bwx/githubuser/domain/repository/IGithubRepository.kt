package com.bwx.githubuser.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IGithubRepository {
    fun getPagingUsers(): Flow<PagingData<UserEntity>>

    fun getDetailUser(login: String): Flow<Resource<User>>
}