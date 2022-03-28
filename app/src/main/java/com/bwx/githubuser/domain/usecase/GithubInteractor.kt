package com.bwx.githubuser.domain.usecase

import androidx.paging.PagingData
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.domain.model.User
import com.bwx.githubuser.domain.repository.IGithubRepository
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val repository: IGithubRepository) : GithubUseCase {
    override fun getPagingUsers(): Flow<PagingData<UserEntity>> = repository.getPagingUsers()
    override fun getDetailUser(login: String): Flow<Resource<User>> =
        repository.getDetailUser(login)
}