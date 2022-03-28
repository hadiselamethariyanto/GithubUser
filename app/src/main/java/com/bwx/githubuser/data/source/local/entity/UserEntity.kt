package com.bwx.githubuser.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "login")
    var login: String,
    @ColumnInfo(name = "repos_url")
    var repos_url: String,
    @ColumnInfo(name = "avatar_url")
    var avatar_url: String,
    @ColumnInfo(name = "company")
    var company: String,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "created_at")
    var created_at: String,
    @ColumnInfo(name = "isFav")
    var isFav: Boolean
)