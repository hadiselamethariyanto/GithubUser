package com.bwx.githubuser.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FollowersResponse(
    @field:SerializedName("id") val id: Int? = 0,
    @field:SerializedName("login") val login: String? = null,
    @field:SerializedName("avatar_url") val avatar_url: String? = null,
    @field:SerializedName("repos_url") val repos_url: String? = null
)