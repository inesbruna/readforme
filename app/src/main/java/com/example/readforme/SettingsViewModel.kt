package com.example.readforme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readforme.data.repository.ConfigRepository
import com.example.readforme.screens.TtsOptions
import com.example.readforme.service.TTSManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    private val ttsManager: TTSManager
) : ViewModel() {
    init {
        viewModelScope.launch {
            loadConfigurations()
        }
    }

    // UI PARAMETERS
    private val _speed = MutableStateFlow(1.0f)
    val speed: StateFlow<Float> = _speed

    private val _language = MutableStateFlow("pt-BR")
    val language: StateFlow<String> = _language

    // UI SETS
    fun setUISpeed(value: Int) {
        _speed.value = TtsOptions.speeds[value]
    }

    fun setUILanguage(value: String) {
        _language.value = value
    }

    // Load Config on Init
    private suspend fun loadConfigurations() {
        val configurations = configRepository.getConfig()

        if (configurations != null) {
            updateSpeed(configurations.speed)
            updateLanguage(configurations.language)

            setSpeed(configurations.speed)
            setLanguage(configurations.language)

            setUISpeed(TtsOptions.speeds.indexOf(configurations.speed))
            setUILanguage(configurations.language)
        } else {
            updateSpeed(1.0f)
            updateLanguage("pt-BR")

            setSpeed(1.0f)
            setLanguage("pt-BR")
        }
    }

    // REPOSITORY UPDATES
    private suspend fun updateSpeed(speed: Float) {
        viewModelScope.launch {
            Log.d("WARNING", "Atualizando speed para: " + speed)
            configRepository.updateSpeed(speed)
        }
    }

    private suspend fun updateLanguage(language: String) {
        viewModelScope.launch {
            configRepository.updateLanguage(language)
        }
    }

    // TTS SETS
    fun setLanguage(language: String){
        viewModelScope.launch {
            val locale = Locale.forLanguageTag(language)

            val location = locale.language // "pt"
            val country = locale.country   // "BR"

            ttsManager.setLanguage(location, country)

            updateLanguage(language)
        }
    }

    fun setSpeed(speed: Float){
        viewModelScope.launch {
            ttsManager.setSpeed(speed)

            updateSpeed(speed)
        }
    }
}