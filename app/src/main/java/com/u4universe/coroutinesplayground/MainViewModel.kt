package com.u4universe.coroutinesplayground

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

private const val TAG = "MyTag"
private val usersList = listOf("Ali", "Hamza", "Umair", "Usman")

class MainViewModel : ViewModel() {

    private val _userData = MutableStateFlow<UiState>(UiState.Loading)
    val userData = _userData.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _userData.value = UiState.Loading
            usersList.forEach {
                fetchDataForUser(it)
            }
            _userData.value = UiState.Complete
        }
    }

    private suspend fun fetchDataForUser(userName: String) {
        Log.d(TAG, "fetchDataForUser: loading data for :$userName")
        delay(2.seconds)
        _userData.value = UiState.Success(userName)
    }
}

sealed interface UiState {
    object Loading : UiState
    object Complete : UiState
    data class Success(val data: String) : UiState
}