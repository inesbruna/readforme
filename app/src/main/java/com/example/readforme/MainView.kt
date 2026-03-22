package com.example.readforme

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import com.example.readforme.screens.CameraScreen
import com.example.readforme.screens.ReadingScreen
import com.example.readforme.screens.ScreenState
import com.example.readforme.screens.SettingsScreen
import java.util.concurrent.ExecutorService


@OptIn(ExperimentalGetImage::class)
@Composable
fun MainView(
    cameraExecutor: ExecutorService,
    screen: ScreenState,
    onNavigate: (ScreenState) -> Unit
) {
    when (screen) {
        is ScreenState.Camera -> CameraScreen(cameraExecutor)
        is ScreenState.Reading -> ReadingScreen()
        is ScreenState.Settings -> SettingsScreen()
    }
}