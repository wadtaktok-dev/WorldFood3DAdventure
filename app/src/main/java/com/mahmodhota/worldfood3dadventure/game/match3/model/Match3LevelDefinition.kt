package com.mahmodhota.worldfood3dadventure.game.match3.model

/**
 * Defines the configuration for a Match-3 level.
 */
data class Match3LevelDefinition(
    val levelNumber: Int,
    val countryId: String,
    val allowedTiles: List<FoodTileType>,
    val goals: List<LevelGoal>,
    val moves: Int,
    val title: String? = null,
    val scoreThresholds: StarThresholds = StarThresholds(800, 1200, 1500)
)

data class StarThresholds(
    val oneStar: Int,
    val twoStars: Int,
    val threeStars: Int
)
