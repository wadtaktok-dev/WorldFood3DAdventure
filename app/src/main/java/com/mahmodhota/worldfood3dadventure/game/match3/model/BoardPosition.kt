package com.mahmodhota.worldfood3dadventure.game.match3.model

import kotlin.math.abs

/**
 * Represents a coordinate (row, column) on the match-3 board.
 */
data class BoardPosition(
    val row: Int,
    val column: Int
) {
    /**
     * Checks if this position is orthogonally adjacent to another.
     */
    fun isAdjacent(other: BoardPosition): Boolean {
        return abs(row - other.row) + abs(column - other.column) == 1
    }
}
