package com.u4universe.coroutinesplayground.di

import android.content.Context
import androidx.room.Room
import com.u4universe.coroutinesplayground.data.local.AppDatabase
import com.u4universe.coroutinesplayground.data.remote.RemoteService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://randomuser.me/"

    private val retrofit: Retrofit by lazy {
        val networkJson = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val remoteService: RemoteService by lazy {
        retrofit.create(RemoteService::class.java)
    }
}

object DatabaseModule {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "playground-database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
