package com.u4universe.coroutinesplayground.di

import android.content.Context
import androidx.room.Room
import com.u4universe.coroutinesplayground.data.AppDatabase
import com.u4universe.coroutinesplayground.network.RemoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
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
