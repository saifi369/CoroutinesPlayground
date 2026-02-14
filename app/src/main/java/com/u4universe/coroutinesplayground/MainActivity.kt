package com.u4universe.coroutinesplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.u4universe.coroutinesplayground.ui.theme.CoroutinesPlaygroundTheme
import com.u4universe.coroutinesplayground.viewmodel.MainScreenState
import com.u4universe.coroutinesplayground.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesPlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = viewModel.uiState.collectAsState()
                    PlaygroundScreen(
                        state = state.value,
                        onLoadData = viewModel::loadData,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PlaygroundScreen(
    state: MainScreenState,
    onLoadData: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is MainScreenState.Idle -> {
                Text(text = "Status: Idle")
            }
            is MainScreenState.Loading -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Status: Loading...")
            }
            is MainScreenState.Success -> {
                Text(text = "Status: ${state.message}", color = Color.Green)
            }
            is MainScreenState.Error -> {
                Text(text = "Error: ${state.error}", color = Color.Red)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onLoadData,
            enabled = state !is MainScreenState.Loading
        ) {
            Text("Download Data")
        }
    }
}