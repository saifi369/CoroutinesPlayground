package com.u4universe.coroutinesplayground.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.u4universe.coroutinesplayground.di.DatabaseModule
import com.u4universe.coroutinesplayground.di.NetworkModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "MyTag"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = DatabaseModule.getDatabase(application)
    private val remoteService = NetworkModule.remoteService

    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val uiState: StateFlow<ScreenState> = _uiState

    fun loadData() {
        // load data
    }
}
