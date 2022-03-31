package com.bwx.githubuser.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.bwx.githubuser.data.Resource
import com.bwx.githubuser.data.source.local.LocalDataSource
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.remote.RemoteDataSource
import com.bwx.githubuser.data.source.remote.network.ApiResponse
import com.bwx.githubuser.data.source.remote.response.DetailUserResponse
import com.bwx.githubuser.data.source.remote.response.RepositoryResponse
import com.bwx.githubuser.domain.model.Repository
import com.bwx.githubuser.domain.model.User
import com.bwx.githubuser.domain.repository.IGithubRepository
import com.bwx.githubuser.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GithubRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IGithubRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingUsers() = Pager(
        config = PagingConfig(10),
        remoteMediator = PageKeyedRemoteMediator(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    ) {
        localDataSource.getPagingUsers()
    }.flow.map { pagingData ->
        pagingData.map { userEntity ->
            DataMapper.mapUserEntityToDomain(userEntity)
        }
    }

    override fun getDetailUser(login: String): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, DetailUserResponse>() {
            override fun loadFromDB(): Flow<User> {
                return localDataSource.getUser(login).map {
                    DataMapper.mapUserEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: User?): Boolean = data != null && data.email == ""

            override suspend fun createCall(): Flow<ApiResponse<DetailUserResponse>> =
                remoteDataSource.getDetailUser(login)

            override suspend fun saveCallResult(data: DetailUserResponse) {
                val user = UserEntity(
                    id = data.id,
                    login = data.login.toString(),
                    repos_url = data.reposUrl.toString(),
                    avatar_url = data.avatarUrl.toString(),
                    company = data.company.toString(),
                    location = data.location.toString(),
                    name = data.name.toString(),
                    email = data.email.toString(),
                    created_at = data.created_at.toString(),
                    public_repos = data.public_repos,
                    following = data.following,
                    followers = data.followers,
                    isFav = false
                )
                localDataSource.updateUser(user)
            }
        }.asFlow()
    }

    override fun getUserRepository(login: String): Flow<Resource<List<Repository>>> {
        return object : NetworkBoundResource<List<Repository>, List<RepositoryResponse>>() {
            override fun loadFromDB(): Flow<List<Repository>> {
                return localDataSource.getRepository(login).map {
                    DataMapper.mapRepositoryEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Repository>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<RepositoryResponse>>> =
                remoteDataSource.getUserRepository(login)

            override suspend fun saveCallResult(data: List<RepositoryResponse>) {
                if (data.isNotEmpty()) {
                    localDataSource.insertRepositories(
                        DataMapper.mapRepositoryResponseToEntities(
                            data, login
                        )
                    )
                }
            }
        }.asFlow()
    }


}