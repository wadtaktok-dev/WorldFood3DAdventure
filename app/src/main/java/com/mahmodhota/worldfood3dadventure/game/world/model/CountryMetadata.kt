package com.mahmodhota.worldfood3dadventure.game.world.model

/**
 * Pure data model for country metadata.
 */
data class CountryMetadata(
    val levelId: String,
    val displayName: String,
    val countryCode: String,
    val flagEmoji: String,
    val travelDescription: String = "",
    val comingSoonText: String? = null
)
