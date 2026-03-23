@file:SuppressLint("SetTextI18n")

package com.u4universe.coroutinesplayground

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.u4universe.coroutinesplayground.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

private const val TAG = "MyTag"

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
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

        binding.btnComposeScreen.setOnClickListener {
            startActivity(Intent(this, MainComposeActivity::class.java))
        }

        binding.btnLoadData.setOnClickListener {
            viewModel.loadData()
        }
    }

    private suspend fun processState(uiState: UiState) {
        when (uiState) {
            UiState.Complete -> {
                binding.tvData.append("\nComplete")
                showCompletionAnimation()
                delay(500)
                moveToNextScreen()
            }

            is UiState.Success -> {
                Log.d(TAG, "processState: data loaded for :${uiState.data} :✅")
                binding.tvData.append("\n${uiState.data} ✅")
            }

            UiState.Loading -> binding.tvData.text = "Loading..."
        }
    }

    private fun showCompletionAnimation() {
        binding.tvCompletionAnimation.apply {
            visibility = View.VISIBLE
            alpha = 0f
            scaleX = 0.5f
            scaleY = 0.5f
            animate()
                .alpha(1f)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(500)
                .start()
        }
    }

    private fun moveToNextScreen() {
        Intent(this, SecondActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
