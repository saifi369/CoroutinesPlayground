package com.u4universe.coroutinesplayground.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.u4universe.coroutinesplayground.data.local.UserEntity
import com.u4universe.coroutinesplayground.di.DatabaseModule
import com.u4universe.coroutinesplayground.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.random.Random

private const val TAG = "MyTag"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DatabaseModule.getDatabase(application)
    private val remoteService = NetworkModule.remoteService

    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val uiState: StateFlow<ScreenState> = _uiState

    fun loadData() {
        performDatabaseOperation()
    }

    private suspend fun downloadJson(): String {
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "downloadJson: Called on Thread: ${Thread.currentThread().name}")
            URL("https://randomuser.me/api/?results=1").readText()
        }
    }

    private fun downloadWithRetrofit() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "downloadWithRetrofit: Called on Thread: ${Thread.currentThread().name}")
                val response = remoteService.getRandomUser()
                if (response.results.isNotEmpty()) {
                    val user = response.results.first()

                    _uiState.value =
                        ScreenState.Success(user.toString())
                } else {
                    _uiState.value = ScreenState.Error("No user found")
                }
            } catch (e: Exception) {
                _uiState.value = ScreenState.Error("${e.message}")
            }
        }
    }

    private fun performDatabaseOperation() {
        viewModelScope.launch {
            try {
                val randomId = Random.nextInt(1000)
                val user = UserEntity(name = "User $randomId", email = "test_$randomId@email.com")

                val rowId = db.userDao().upsertUser(user)

                _uiState.value =
                    ScreenState.Success("Inserted ${user.name}, \n\n Row ID: $rowId")
            } catch (e: Exception) {
                _uiState.value = ScreenState.Error("DB Error: ${e.message}")
            }
        }
    }
}
