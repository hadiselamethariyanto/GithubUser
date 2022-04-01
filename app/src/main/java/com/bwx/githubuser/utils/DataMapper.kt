package com.bwx.githubuser.utils

import com.bwx.githubuser.data.source.local.entity.FollowersEntity
import com.bwx.githubuser.data.source.local.entity.FollowingEntity
import com.bwx.githubuser.data.source.local.entity.RepositoryEntity
import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.data.source.remote.response.FollowersResponse
import com.bwx.githubuser.data.source.remote.response.FollowingResponse
import com.bwx.githubuser.data.source.remote.response.RepositoryResponse
import com.bwx.githubuser.data.source.remote.response.UserResponse
import com.bwx.githubuser.domain.model.Followers
import com.bwx.githubuser.domain.model.Following
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

    fun mapFollowerEntitiesToDomain(input: List<FollowersEntity>): List<Followers> {
        return input.map {
            Followers(
                id = it.id,
                login = it.login,
                avatar_url = it.avatar_url,
                repos_url = it.repos_url
            )
        }
    }

    fun mapFollowersResponseToEntities(
        input: List<FollowersResponse>,
        login: String
    ): List<FollowersEntity> {
        return input.map {
            FollowersEntity(
                id = it.id ?: 0,
                login = it.login.toString(),
                avatar_url = it.avatar_url.toString(),
                repos_url = it.repos_url.toString(),
                login_followers = login
            )
        }
    }

    fun mapFollowingEntitiesToDomain(input: List<FollowingEntity>): List<Following> {
        return input.map {
            Following(
                id = it.id,
                login = it.login,
                avatar_url = it.avatar_url,
                repos_url = it.repos_url
            )
        }
    }

    fun mapFollowingResponseToEntities(
        input: List<FollowingResponse>,
        login: String
    ): List<FollowingEntity> {
        return input.map {
            FollowingEntity(
                id = it.id ?: 0,
                login = it.login.toString(),
                avatar_url = it.avatar_url.toString(),
                repos_url = it.repos_url.toString(),
                login_following = login
            )
        }
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