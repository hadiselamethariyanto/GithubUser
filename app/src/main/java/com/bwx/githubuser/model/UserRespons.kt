package com.bwx.githubuser.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class UserRespons(
    @field:SerializedName("gists_url")
    val gistsUrl: String?,

    @field:SerializedName("repos_url")
    val reposUrl: String?,

    @field:SerializedName("following_url")
    val followingUrl: String?,

    @field:SerializedName("starred_url")
    val starredUrl: String?,

    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("followers_url")
    val followersUrl: String?,

    @field:SerializedName("type")
    val type: String?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("subscriptions_url")
    val subscriptionsUrl: String?,

    @field:SerializedName("score")
    val score: Double?,

    @field:SerializedName("received_events_url")
    val receivedEventsUrl: String?,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("events_url")
    val eventsUrl: String?,

    @field:SerializedName("html_url")
    val htmlUrl: String?,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("gravatar_id")
    val gravatarId: String?,

    @field:SerializedName("node_id")
    val nodeId: String?,

    @field:SerializedName("organizations_url")
    val organizationsUrl: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double?,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean?,
        parcel.readValue(Int::class.java.classLoader) as? Int?,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(gistsUrl)
        parcel.writeString(reposUrl)
        parcel.writeString(followingUrl)
        parcel.writeString(starredUrl)
        parcel.writeString(login)
        parcel.writeString(followersUrl)
        parcel.writeString(type)
        parcel.writeString(url)
        parcel.writeString(subscriptionsUrl)
        parcel.writeValue(score)
        parcel.writeString(receivedEventsUrl)
        parcel.writeString(avatarUrl)
        parcel.writeString(eventsUrl)
        parcel.writeString(htmlUrl)
        parcel.writeValue(siteAdmin)
        parcel.writeValue(id)
        parcel.writeString(gravatarId)
        parcel.writeString(nodeId)
        parcel.writeString(organizationsUrl)
    }

    companion object CREATOR : Parcelable.Creator<UserRespons> {
        override fun createFromParcel(parcel: Parcel): UserRespons {
            return UserRespons(parcel)
        }

        override fun newArray(size: Int): Array<UserRespons?> {
            return arrayOfNulls(size)
        }
    }

}
