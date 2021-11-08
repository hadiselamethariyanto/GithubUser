package com.bwx.githubuser.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("repos_url")
	val reposUrl: String? = null,

	@field:SerializedName("bio")
	val bio: Any? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

)
