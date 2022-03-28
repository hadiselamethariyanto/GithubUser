package com.bwx.githubuser.utils

import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.remote.response.UserResponse
import com.bwx.githubuser.domain.model.User

object DataMapper {

    fun mapUserEntityToDomain(input: UserEntity) =
        User(
            id = input.id,
            login = input.login,
            avatar_url = input.avatar_url,
            repos_url = input.repos_url,
            name = input.name,
            email = input.email,
            created_at = input.created_at
        )

    fun mapUsersResponseToEntities(input: List<UserResponse>): List<UserEntity> {
        val users = ArrayList<UserEntity>()
        input.map {
            val user = UserEntity(
                id = it.id,
                login = it.login,
                repos_url = it.reposUrl,
                avatar_url = it.avatarUrl,
                company = "",
                location = "",
                name = "",
                email = "",
                created_at = "",
                isFav = false
            )
            users.add(user)
        }
        return users
    }

}