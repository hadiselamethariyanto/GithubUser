package com.bwx.githubuser.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @field:SerializedName("repos_url")
    val reposUrl: String? = null,
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("company")
    val company: String? = null,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("location")
    val location: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("public_repos")
    var public_repos: Int,
    @field:SerializedName("following")
    var following: Int,
    @field:SerializedName("followers")
    var followers: Int,
    @field:SerializedName("created_at")
    val created_at: String? = null
)
