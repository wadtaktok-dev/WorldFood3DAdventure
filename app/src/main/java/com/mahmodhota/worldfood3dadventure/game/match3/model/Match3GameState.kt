package com.mahmodhota.worldfood3dadventure.game.match3.model

/**
 * Game status for Match-3 levels.
 */
enum class GameStatus {
    PLAYING,
    WON,
    LOST
}

/**
 * Top-level immutable state of a Match-3 game session.
 */
data class Match3GameState(
    val board: Match3Board,
    val score: Int = 0,
    val movesRemaining: Int,
    val collectedCounts: Map<FoodTileType, Int> = emptyMap(),
    val goals: List<LevelGoal>,
    val status: GameStatus = GameStatus.PLAYING
)
