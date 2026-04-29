package com.example.readforme

import android.graphics.Bitmap.Config
import android.speech.tts.Voice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readforme.data.local.ConfigEntity
import com.example.readforme.data.repository.ConfigRepository
import com.example.readforme.data.repository.TextRepository
import com.example.readforme.screens.ScreenState
import com.example.readforme.service.TTSManager
import com.google.android.gms.common.util.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val textRepository: TextRepository,
    private val ttsManager: TTSManager
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

    // Text Recognized + Room
    fun onTextRecognized(text: String) {
        _recognizedText.value = text

        viewModelScope.launch {
            textRepository.saveText(text)
        }
    }

    fun clearRecognizedText() {
        _recognizedText.value = ""
    }

    fun clearDatabase() {
        viewModelScope.launch {
            textRepository.deleteAllTexts()
        }
    }

    fun onSpeakClicked(text: String) {
        ttsManager.speak(text)
    }

    fun stopSpeak(){
        ttsManager.stop()
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}


