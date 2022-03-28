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
            created_at = ""
        )
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
            isFav = false
        )
    }
}