package com.bwx.githubuser.data.source.local

import androidx.room.Query
import com.bwx.githubuser.data.source.local.entity.FollowersEntity
import com.bwx.githubuser.data.source.local.entity.FollowingEntity
import com.bwx.githubuser.data.source.local.entity.RepositoryEntity
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.local.room.GithubDao

class LocalDataSource(private val dao: GithubDao) {

    suspend fun insertUsers(users: List<UserEntity>) = dao.insertUsers(users)

    suspend fun insertRepositories(repositories: List<RepositoryEntity>) =
        dao.insertRepositories(repositories)

    suspend fun insertFollowing(following: List<FollowingEntity>) = dao.insertFollowing(following)

    suspend fun insertFollowers(followers: List<FollowersEntity>) = dao.insertFollowers(followers)

    suspend fun updateUser(user: UserEntity) = dao.updateUser(user)

    suspend fun getRemoteKey() = dao.getRemoteKey()

    fun getPagingUsers() = dao.getPagingUsers()

    fun getUser(login: String) = dao.getUser(login)

    fun getRepository(login: String) = dao.getRepository(login)

    fun getUserFollowing(login: String) = dao.getUserFollowing(login)

    fun getUserFollowers(login: String) = dao.getUserFollowers(login)
}