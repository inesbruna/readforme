package com.example.readforme

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readforme.data.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TextRepository
) : ViewModel() {
    private val _currentScreen = MutableStateFlow<ScreenState>(ScreenState.Camera)
    val currentScreen = _currentScreen.asStateFlow()

    fun navigate(screen: ScreenState) {
        if (screen != ScreenState.Camera) {
            clearRecognizedText()
        }
        _currentScreen.value = screen
    }

    private val _cameraPermissionGranted = MutableStateFlow(false)

    private val _recognizedText = MutableStateFlow("")
    val recognizedText = _recognizedText.asStateFlow()

    fun onPermissionResult(granted: Boolean) {
        _cameraPermissionGranted.value = granted
    }

    fun onTextRecognized(text: String) {
        _recognizedText.value = text

        viewModelScope.launch {
            repository.saveText(text)
        }
    }

    fun clearRecognizedText() {
        _recognizedText.value = ""
    }
}


