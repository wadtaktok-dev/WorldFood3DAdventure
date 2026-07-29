package com.mahmodhota.worldfood3dadventure.data.progress.model

/**
 * Persisted player profile data.
 */
data class PlayerProgress(
    val lives: Int = 5,
    val coins: Int = 100,
    val xp: Int = 0,
    val level: Int = 1,
    val totalStars: Int = 0,
    val username: String = "Traveler"
)
