package com.mahmodhota.worldfood3dadventure.game.match3.model

/**
 * Result of a match detection step.
 */
data class MatchResult(
    val matchGroups: List<MatchGroup>,
    val uniquePositions: Set<BoardPosition>,
    val specialTilesToSpawn: Map<BoardPosition, SpecialTileType> = emptyMap()
) {
    val hasMatches: Boolean get() = matchGroups.isNotEmpty()
    val totalMatchedTiles: Int get() = uniquePositions.size
}
