package com.example.readforme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readforme.data.repository.TextRepository
import com.example.readforme.screens.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TextRepository
) : ViewModel() {

    /* FOR DEBUG ONLY
    init {
        viewModelScope.launch {
            repository.getAllTexts().collect {

            }
        }
    }
     */

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

    fun clearDatabase() {
        viewModelScope.launch {
            repository.deleteAllTexts()
        }
    }
}


