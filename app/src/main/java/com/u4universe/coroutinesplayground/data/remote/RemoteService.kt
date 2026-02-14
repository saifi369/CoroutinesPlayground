package com.u4universe.coroutinesplayground.data.remote

import retrofit2.http.GET

interface RemoteService {
    @GET("api/?results=1")
    suspend fun getRandomUser(): RandomUserResponse
}
