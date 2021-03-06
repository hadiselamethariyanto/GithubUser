package com.bwx.githubuser.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bwx.githubuser.data.source.local.entity.FollowersEntity
import com.bwx.githubuser.data.source.local.entity.FollowingEntity
import com.bwx.githubuser.data.source.local.entity.RepositoryEntity
import com.bwx.githubuser.data.source.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, RepositoryEntity::class, FollowingEntity::class, FollowersEntity::class],
    version = 1
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao
}

