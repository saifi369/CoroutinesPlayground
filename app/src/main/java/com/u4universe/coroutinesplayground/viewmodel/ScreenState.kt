package com.u4universe.coroutinesplayground.viewmodel

sealed class ScreenState {
    object Idle : ScreenState()
    object Loading : ScreenState()
    data class Success(val data: String) : ScreenState()
    data class Error(val error: String) : ScreenState()
}
