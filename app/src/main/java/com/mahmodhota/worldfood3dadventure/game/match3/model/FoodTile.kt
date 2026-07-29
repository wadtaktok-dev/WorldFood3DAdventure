package com.mahmodhota.worldfood3dadventure.game.match3.model

/**
 * Immutable data model for a single tile on the match-3 board.
 */
data class FoodTile(
    val id: Long,
    val type: FoodTileType,
    val specialType: SpecialTileType = SpecialTileType.NONE
)
