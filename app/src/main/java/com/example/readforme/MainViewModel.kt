package com.example.readforme

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

    private val _cameraPermissionGranted = MutableStateFlow(false)
    val cameraPermissionGranted = _cameraPermissionGranted.asStateFlow()

    private val _recognizedText = MutableStateFlow("Aguardando leitura...")
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
}


