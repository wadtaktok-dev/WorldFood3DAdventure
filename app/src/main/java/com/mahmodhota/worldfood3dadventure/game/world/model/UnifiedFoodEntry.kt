package com.mahmodhota.worldfood3dadventure.game.world.model

/**
 * Scalable data model for Food Book entries.
 */
data class UnifiedFoodEntry(
    val id: String,
    val name: String,
    val country: String,
    val description: String,
    val region: String = "National",
    val history: String = "",
    val ingredients: List<String> = emptyList(),
    val interestingFact: String = "",
    val nutritionInfo: String = "",
    val unlocked: Boolean = false
)
