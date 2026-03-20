package com.example.readforme

sealed class ScreenState {
    object Camera : ScreenState()
    object Reading : ScreenState()
    object Settings : ScreenState()
}