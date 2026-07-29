package com.mahmodhota.worldfood3dadventure.data.progress.model

/**
 * Persisted user settings.
 */
data class GameSettings(
    val musicVolume: Float = 1.0f,
    val sfxVolume: Float = 1.0f,
    val vibrationEnabled: Boolean = true,
    val language: String = "en",
    val darkMode: Boolean = true
)
