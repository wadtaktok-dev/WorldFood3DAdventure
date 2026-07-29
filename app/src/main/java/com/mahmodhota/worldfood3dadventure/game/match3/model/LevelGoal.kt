package com.mahmodhota.worldfood3dadventure.game.match3.model

/**
 * Pure data model for a level objective.
 */
sealed class LevelGoal {
    data class ScoreTarget(val target: Int) : LevelGoal()
    data class CollectFood(val type: FoodTileType, val amount: Int) : LevelGoal()
}
