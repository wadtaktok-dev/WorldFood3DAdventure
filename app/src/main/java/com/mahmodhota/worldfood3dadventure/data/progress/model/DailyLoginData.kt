package com.mahmodhota.worldfood3dadventure.data.progress.model

/**
 * Data for daily login tracking.
 */
data class DailyLoginData(
    val lastLoginDate: Long = 0,
    val loginStreak: Int = 0
)
