package com.u4universe.coroutinesplayground.network

import retrofit2.http.GET

data class RandomUserResponse(
    val results: List<UserResult>
)

data class UserResult(
    val name: Name,
    val email: String,
    val picture: Picture
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

interface RemoteService {
    @GET("api/")
    suspend fun getRandomUser(): RandomUserResponse
}
