package com.u4universe.coroutinesplayground.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.u4universe.coroutinesplayground.data.User
import com.u4universe.coroutinesplayground.di.DatabaseModule
import com.u4universe.coroutinesplayground.di.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DatabaseModule.getDatabase(application)
    private val service = NetworkModule.remoteService

    private val _uiState = MutableStateFlow<MainScreenState>(MainScreenState.Idle)
    val uiState: StateFlow<MainScreenState> = _uiState

    fun loadData() {
        _uiState.value = MainScreenState.Loading
        downloadJsonMainThread()
    }

    // 1. Plain JSON Download (Blocking Main Thread)
    private fun downloadJsonMainThread() {
        viewModelScope.launch {
            try {
                // This blocks the main thread!
                // We'll read the first line of the logical JSON response for simplicity to start with
                val result = URL("https://randomuser.me/api/").readText()
                    // taking a substring to keep the UI clean if it's too long
                    .take(100) + "..." 
                _uiState.value = MainScreenState.Success("Blocking Result (truncated): $result")
            } catch (e: Exception) {
                _uiState.value = MainScreenState.Error("Blocking Error: ${e.message}")
            }
        }
    }
}
