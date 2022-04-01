package com.bwx.githubuser.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "followers")
data class FollowersEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") val avatar_url: String,
    @ColumnInfo(name = "repos_url") val repos_url: String,
    @ColumnInfo(name = "login_followers") val login_followers: String
)