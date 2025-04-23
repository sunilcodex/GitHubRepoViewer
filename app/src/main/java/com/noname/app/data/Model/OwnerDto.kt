package com.noname.app.data.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OwnerDto(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Serializable
