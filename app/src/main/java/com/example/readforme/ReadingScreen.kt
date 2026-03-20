package com.example.readforme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ReadingScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Text(
        text = "TODO: Reading Screen",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}