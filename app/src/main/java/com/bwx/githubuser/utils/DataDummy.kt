package com.bwx.githubuser.utils

import com.bwx.githubuser.data.source.local.entity.UserEntity
import com.bwx.githubuser.domain.model.User

object DataDummy {

    fun generateUserDummy(): User {
        return User(
            id = 1,
            login = "hadi",
            repos_url = "",
            avatar_url = "",
            name = "hadi",
            email = "muhammad.hadi.selamet@gmail.com",
            created_at = "",
            company = "",
            location = "",
            public_repos = 0,
            following = 0,
            followers = 0
        )
    }

    fun generateUsersEntityDummy(): List<UserEntity> {
        val users = ArrayList<UserEntity>()
        users.add(
            UserEntity(
                id = 1,
                login = "hadi",
                repos_url = "",
                avatar_url = "",
                company = "",
                location = "Banyuwangi",
                name = "hadi",
                email = "muhammad.hadi.selamet@gmail.com",
                created_at = "",
                public_repos = 0,
                following = 0,
                followers = 0,
                isFav = false
            )
        )

        users.add(
            UserEntity(
                id = 2,
                login = "selamet",
                repos_url = "",
                avatar_url = "",
                company = "",
                location = "Banyuwangi",
                name = "selamet",
                email = "selamet@gmail.com",
                created_at = "",
                public_repos = 0,
                following = 0,
                followers = 0,
                isFav = false
            )
        )

        return users
    }

    fun generateUserEntityDummy(): UserEntity {
        return UserEntity(
            id = 1,
            login = "hadi",
            repos_url = "",
            avatar_url = "",
            company = "",
            location = "Banyuwangi",
            name = "hadi",
            email = "muhammad.hadi.selamet@gmail.com",
            created_at = "",
            public_repos = 0,
            following = 0,
            followers = 0,
            isFav = false
        )
    }
}