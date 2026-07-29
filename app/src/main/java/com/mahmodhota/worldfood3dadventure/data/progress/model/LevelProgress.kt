package com.mahmodhota.worldfood3dadventure.data.progress.model

/**
 * Persisted progress for a single level.
 */
data class LevelProgress(
    val levelNumber: Int,
    val countryId: String,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val bestStars: Int = 0,
    val bestScore: Int = 0,
    val rewardClaimed: Boolean = false
)
