package com.bwx.githubuser.data.source.local

import androidx.room.Query
import com.bwx.githubuser.data.source.local.entity.RepositoryEntity
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.local.room.GithubDao

class LocalDataSource(private val dao: GithubDao) {

    suspend fun insertUsers(users: List<UserEntity>) = dao.insertUsers(users)

    suspend fun insertRepositories(repositories: List<RepositoryEntity>) =
        dao.insertRepositories(repositories)

    suspend fun updateUser(user: UserEntity) = dao.updateUser(user)

    suspend fun getRemoteKey() = dao.getRemoteKey()

    fun getPagingUsers() = dao.getPagingUsers()

    fun getUser(login: String) = dao.getUser(login)

    fun getRepository(login: String) = dao.getRepository(login)
}