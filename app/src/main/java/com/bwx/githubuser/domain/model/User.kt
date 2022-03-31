package com.bwx.githubuser.domain.model

data class User(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val repos_url: String,
    val name: String,
    val email: String,
    val company: String,
    val location: String,
    val public_repos:Int,
    val following:Int,
    val followers:Int,
    val created_at: String
)