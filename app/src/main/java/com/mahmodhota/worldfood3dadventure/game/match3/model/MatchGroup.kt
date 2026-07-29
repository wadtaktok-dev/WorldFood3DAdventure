package com.mahmodhota.worldfood3dadventure.game.match3.model

/**
 * Details of a single detected match group.
 */
data class MatchGroup(
    val positions: Set<BoardPosition>,
    val direction: MatchDirection,
    val type: FoodTileType,
    val creationType: SpecialTileType = SpecialTileType.NONE,
    val creationPoint: BoardPosition? = null
) {
    val length: Int get() = positions.size
}
