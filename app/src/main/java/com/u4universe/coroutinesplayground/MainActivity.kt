package com.u4universe.coroutinesplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.u4universe.coroutinesplayground.ui.theme.CoroutinesPlaygroundTheme
import com.u4universe.coroutinesplayground.viewmodel.ScreenState
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
    state: ScreenState,
    onLoadData: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        when (state) {
            is ScreenState.Loading -> {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }

            is ScreenState.Success -> {
                Text(
                    text = state.data,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }

            is ScreenState.Error -> {
                Text(
                    text = state.error,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }

            else -> {
                Text(
                    text = "Welcome to U4Universe",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 52.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Button(
            onClick = onLoadData,
            enabled = state !is ScreenState.Loading
        ) {
            Text("Load Data")
        }
    }
}

@Preview
@Composable
fun PlaygroundScreenPreview() {
    CoroutinesPlaygroundTheme {
        PlaygroundScreen(
            state = ScreenState.Idle,
            onLoadData = {},
        )
    }
}