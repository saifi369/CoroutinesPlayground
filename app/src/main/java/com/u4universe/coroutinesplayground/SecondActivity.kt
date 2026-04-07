package com.u4universe.coroutinesplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.u4universe.coroutinesplayground.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
