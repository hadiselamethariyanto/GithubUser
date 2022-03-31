package com.bwx.githubuser.utils

import com.bwx.githubuser.data.source.local.entity.RepositoryEntity
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.remote.response.RepositoryResponse
import com.bwx.githubuser.data.source.remote.response.UserResponse
import com.bwx.githubuser.domain.model.Repository
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
            created_at = input.created_at,
            company = input.company,
            location = input.location,
            public_repos = input.public_repos,
            following = input.following,
            followers = input.followers
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
                public_repos = 0,
                following = 0,
                followers = 0,
                isFav = false
            )
            users.add(user)
        }
        return users
    }

    fun mapRepositoryEntitiesToDomain(input: List<RepositoryEntity>): List<Repository> {
        return input.map {
            Repository(
                id = it.id,
                name = it.name,
                full_name = it.full_name,
                description = it.description,
                language = it.language
            )
        }
    }

    fun mapRepositoryResponseToEntities(
        input: List<RepositoryResponse>,
        login: String
    ): List<RepositoryEntity> {
        val repositories = ArrayList<RepositoryEntity>()
        input.map {
            val repository = RepositoryEntity(
                id = it.id,
                name = it.name.toString(),
                full_name = it.full_name.toString(),
                description = it.description.toString(),
                language = it.language.toString(),
                login = login
            )
            repositories.add(repository)
        }
        return repositories
    }

}