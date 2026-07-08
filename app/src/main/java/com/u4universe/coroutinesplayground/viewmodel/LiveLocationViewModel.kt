package com.u4universe.coroutinesplayground.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

private const val TAG = "LiveLocationViewModel"

class LiveLocationViewModel : ViewModel() {

    // ⚠️ Bug: cold flow with no lifecycle awareness
    // Keeps polling GPS even when the app is in the background
    val gpsLocationFlow = flow<String> {
        var lat = 12.3456
        var lng = -123.4567
        while (true) {
            Log.d(TAG, "GPS flow: polling location from sensor...")
            emit("Lat: ${lat.toString().take(7)}\nLng: ${lng.toString().take(9)}")
            lat += 0.0001
            lng += 0.0001
            delay(1000)
        }
    }
}
