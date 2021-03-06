package com.bwx.githubuser.domain.usecase

import androidx.paging.PagingData
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.domain.model.Followers
import com.bwx.githubuser.domain.model.Following
import com.bwx.githubuser.domain.model.Repository
import com.bwx.githubuser.domain.model.User
import com.bwx.githubuser.domain.repository.IGithubRepository
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val repository: IGithubRepository) : GithubUseCase {
    override fun getPagingUsers(): Flow<PagingData<User>> = repository.getPagingUsers()
    override fun getDetailUser(login: String): Flow<Resource<User>> =
        repository.getDetailUser(login)

    override fun getUserRepository(login: String): Flow<Resource<List<Repository>>> =
        repository.getUserRepository(login)

    override fun getUserFollowing(login: String): Flow<Resource<List<Following>>> =
        repository.getUserFollowing(login)

    override fun getUserFollowers(login: String): Flow<Resource<List<Followers>>> =
        repository.getUserFollowers(login)
}