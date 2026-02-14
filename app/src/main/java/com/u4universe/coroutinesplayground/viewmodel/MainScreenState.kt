package com.u4universe.coroutinesplayground.viewmodel

sealed class MainScreenState {
    object Idle : MainScreenState()
    object Loading : MainScreenState()
    data class Success(val message: String) : MainScreenState()
    data class Error(val error: String) : MainScreenState()
}
