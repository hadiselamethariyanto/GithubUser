package com.bwx.githubuser.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("full_name")
    val full_name: String? = null,
    @field:SerializedName("description")
    val description: String? = null,
    @field:SerializedName("language")
    val language: String? = null
)