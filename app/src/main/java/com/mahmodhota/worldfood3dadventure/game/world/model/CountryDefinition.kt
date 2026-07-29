package com.mahmodhota.worldfood3dadventure.game.world.model

import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3LevelDefinition

/**
 * Aggregates all data related to a single country.
 */
data class CountryDefinition(
    val id: String,
    val metadata: CountryMetadata,
    val levels: List<Match3LevelDefinition>,
    val foodEntries: List<UnifiedFoodEntry> = emptyList(),
    val isComingSoon: Boolean = false
)
