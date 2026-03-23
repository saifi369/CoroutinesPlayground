package com.u4universe.coroutinesplayground.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "MyTag"

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun testMainDispatcher() {
        Log.d(TAG, "--- Dispatchers.Main ---")
    }

    fun testMainImmediateDispatcher() {
        Log.d(TAG, "--- Dispatchers.Main.immediate ---")
    }

    fun incrementCounter() {
        _counter.update { it + 1 }
        Log.d(TAG, "incrementCounter: New value: ${_counter.value}")
    }
}
