package com.bwx.githubuser.domain.model

data class User(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val repos_url: String,
    val name: String,
    val email: String,
    val created_at: String
)