package com.mahmodhota.worldfood3dadventure.data.progress.model

/**
 * Global game statistics.
 */
data class GameStatistics(
    val firstInstallDate: Long = 0,
    val lastPlayedDate: Long = 0,
    val totalPlayTimeMillis: Long = 0,
    val totalMatches: Int = 0,
    val totalCompletedLevels: Int = 0,
    val highestCombo: Int = 0,
    val longestPlaySessionMillis: Long = 0
)
