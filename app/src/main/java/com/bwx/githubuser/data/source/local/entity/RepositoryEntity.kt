package com.bwx.githubuser.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository")
data class RepositoryEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val full_name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "login") val login: String
)