package com.u4universe.coroutinesplayground

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.u4universe.coroutinesplayground.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue
import kotlin.time.Duration.Companion.seconds

private const val TAG = "MyTag"
private val usersList = listOf("Ali", "Hamza", "Umair", "Usman")

class MainActivity : AppCompatActivity() {

//    val scope = CoroutineScope(Dispatchers.Main)

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnNextScreen.setOnClickListener {
            moveToNextScreen()
        }

        binding.btnLoadData.setOnClickListener {
            lifecycleScope.launch {
                binding.tvData.text = "Loading..."
                usersList.forEach {
                    fetchDataForUser(it)
                }
                binding.tvData.append("\nComplete")
                moveToNextScreen()
            }
        }
    }

    suspend fun fetchDataForUser(userName: String) {
        Log.d(TAG, "fetchDataForUser: loading data for :$userName")
        delay(2.seconds)
        binding.tvData.append("\n$userName ✅")
    }

    private fun moveToNextScreen() {
        Intent(this, SecondActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        scope.cancel()
//    }
}
