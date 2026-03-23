package com.u4universe.coroutinesplayground

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "MyTag"

class LiveLocationViewModel : ViewModel() {

    val gpsLocationFlow: Flow<String> = flow {
        var lat = 37.7749
        var lng = -122.4194
        while (true) {
            Log.d(TAG, "GPS flow: polling location from sensor...")
            emit("Lat: ${lat.toString().take(7)}\nLng: ${lng.toString().take(9)}")
            lat += 0.0001
            lng += 0.0001
            delay(1000)
        }
    }
}
