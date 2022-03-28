package com.bwx.githubuser.data.source.local

import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.local.room.GithubDao

class LocalDataSource(private val dao: GithubDao) {

    suspend fun insertUsers(users: List<UserEntity>) = dao.insertUsers(users)

    suspend fun updateUser(user: UserEntity) = dao.updateUser(user)

    suspend fun getRemoteKey() = dao.getRemoteKey()

    fun getPagingUsers() = dao.getPagingUsers()

    fun getUser(login: String) = dao.getUser(login)
}