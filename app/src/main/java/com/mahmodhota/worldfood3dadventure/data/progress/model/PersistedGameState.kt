package com.mahmodhota.worldfood3dadventure.data.progress.model

/**
 * Root object for the entire persisted game state.
 */
data class PersistedGameState(
    val player: PlayerProgress = PlayerProgress(),
    val countries: Map<String, CountryGameProgress> = emptyMap(),
    val settings: GameSettings = GameSettings(),
    val stats: GameStatistics = GameStatistics(),
    val dailyLogin: DailyLoginData = DailyLoginData(),
    val lastSelectedCountry: String = "germany",
    val lastSelectedLevel: Int = 1,
    val isChapter1Completed: Boolean = false,
    val worldExplorerBadge: Boolean = false,
    val schemaVersion: Int = 1
)
