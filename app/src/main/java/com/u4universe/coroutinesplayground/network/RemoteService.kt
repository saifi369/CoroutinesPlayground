package com.u4universe.coroutinesplayground.network

import retrofit2.http.GET

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

interface RemoteService {
    @GET("posts/1")
    suspend fun getPost(): Post
}
