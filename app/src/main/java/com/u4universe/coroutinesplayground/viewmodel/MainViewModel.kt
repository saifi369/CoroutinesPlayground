package com.u4universe.coroutinesplayground.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

private const val TAG = "MyTag"
private val usersList = listOf("Ali", "Hamza", "Umair", "Usman")

class MainViewModel : ViewModel() {

    private val _userData = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val userData = _userData.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            _userData.value = ScreenState.Loading
            usersList.forEach {
                fetchDataForUser(it)
            }
            _userData.value = ScreenState.Complete
        }
    }

    private suspend fun fetchDataForUser(userName: String) {
        Log.d(TAG, "fetchDataForUser: loading data for :$userName")
        delay(1.seconds)
        _userData.value = ScreenState.Success(userName)
    }
}

sealed interface ScreenState {
    object Idle : ScreenState
    object Loading : ScreenState
    data class Success(val data: String) : ScreenState
    object Complete : ScreenState
}
