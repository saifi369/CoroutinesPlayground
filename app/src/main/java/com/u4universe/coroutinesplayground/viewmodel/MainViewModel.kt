package com.u4universe.coroutinesplayground.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.u4universe.coroutinesplayground.di.DatabaseModule
import com.u4universe.coroutinesplayground.di.NetworkModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DatabaseModule.getDatabase(application)
    private val remoteService = NetworkModule.remoteService

    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val uiState: StateFlow<ScreenState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = ScreenState.Loading
            val users = remoteService.getRandomUser().results

            Log.d("MyTag", "loadData: Called on Thread: ${Thread.currentThread().name}")

            if (users.isEmpty()) {
                _uiState.value = ScreenState.Error("No data found")
            } else {
                _uiState.value = ScreenState.Success(users.first().toString())
            }

            while (true) {
                Log.d("MyTag", "loadData: called")
                delay(2.seconds)
            }
        }

        viewModelScope.launch {
            try {
                Log.d("MyTag", "Coroutine 2 started")
                delay(1.seconds)
                throw RuntimeException("Error")
            } catch (e: Exception) {
                Log.d("MyTag", "Exception: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MyTag", "onCleared: Called")
    }
}
