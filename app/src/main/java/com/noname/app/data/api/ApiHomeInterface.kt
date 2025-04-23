package com.noname.app.data.api

import com.noname.app.data.Model.RepositoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiHomeInterface {


    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Response<List<RepositoryDto>>

}