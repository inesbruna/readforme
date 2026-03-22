package com.example.readforme.screens

sealed class ScreenState {
    object Camera : ScreenState()
    object Reading : ScreenState()
    object Settings : ScreenState()
}