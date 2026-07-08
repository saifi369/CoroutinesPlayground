package com.u4universe.coroutinesplayground

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.u4universe.coroutinesplayground.databinding.ActivityMainBinding
import com.u4universe.coroutinesplayground.viewmodel.MainViewModel
import com.u4universe.coroutinesplayground.viewmodel.ScreenState
import com.u4universe.coroutinesplayground.viewmodel.ScreenState.Complete
import com.u4universe.coroutinesplayground.viewmodel.ScreenState.Idle
import com.u4universe.coroutinesplayground.viewmodel.ScreenState.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoadData.setOnClickListener {
            viewModel.loadData()
        }

        binding.btnLocationScreen.setOnClickListener {
            startActivity(Intent(this, LocationActivity::class.java))
        }

        lifecycleScope.launch {
            viewModel.userData.collect { state ->
                handleState(state)
            }
        }
    }

    private fun handleState(state: ScreenState) {
        when (state) {
            Idle -> binding.tvData.text = "Welcome!"

            ScreenState.Loading -> binding.tvData.text = "Loading..."

            is Success -> binding.tvData.append("\n${state.data} ✅")

            Complete -> {
                lifecycleScope.launch { showAnimationAndNavigate() }
            }
        }
    }

    private suspend fun showAnimationAndNavigate() {
        binding.ivSuccess.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(1000).start()
        }
        delay(1000)
        startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        finish()
    }
}
