package com.u4universe.coroutinesplayground

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.u4universe.coroutinesplayground.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

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

        binding.btnLoadData.setOnClickListener {
            viewModel.loadData()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userData.collect { data ->
                    if (data == "Loading...") {
                        binding.tvData.text = data
                    } else if (data == "Complete") {
                        binding.tvData.append("\n$data")
                        moveToNextScreen()
                    } else if (data.isNotEmpty()) {
                        binding.tvData.append("\n$data ✅")
                    }
                }
            }
        }
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
