package com.example.readforme.models

data class TtsRequest(
    val text: String,
    val language: String = "pt-BR",
    val speed: Float = 1.0f
)