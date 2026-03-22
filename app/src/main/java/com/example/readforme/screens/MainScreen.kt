package com.example.readforme.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.readforme.MainView
import com.example.readforme.MainViewModel
import com.example.readforme.presentation.components.Drawer
import java.util.concurrent.ExecutorService

@Composable
fun MainScreen(
    cameraExecutor: ExecutorService,
    viewModel: MainViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val currentScreen = viewModel.currentScreen.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.clearDatabase()
    }

    Drawer(
        currentScreen = currentScreen,
        onNavigate = { viewModel.navigate(it)}
    ) { padding ->
        MainView(
            cameraExecutor = cameraExecutor,
            screen = currentScreen,
            onNavigate = { viewModel.navigate(it) }
        )
    }
}