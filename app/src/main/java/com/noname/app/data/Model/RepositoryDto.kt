package com.noname.app.data.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RepositoryDto(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forks: Int,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("archived")
    val archived: Boolean,
    val visibility: String?,
    val owner: OwnerDto
) : Serializable {
    companion object {
        val LOADING = RepositoryDto(
            id = -1L,
            name = "LOADING",
            description = null,
            language = null,
            stars = 0,
            forks = 0,
            htmlUrl = "",
            archived = false,
            visibility = null,
            owner = OwnerDto(
                login = "",
                avatarUrl = ""
            )
        )
    }
}

