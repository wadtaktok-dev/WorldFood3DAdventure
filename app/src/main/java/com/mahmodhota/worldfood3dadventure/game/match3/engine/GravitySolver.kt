package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTile
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3Board

/**
 * Handles the downward movement of tiles into empty spaces.
 */
object GravitySolver {

    /**
     * Collapses tiles downward to fill null spaces.
     * Note: This assumes empty spaces are represented as missing tiles or nulls in a temporary structure.
     * Since Match3Board is immutable, we use a list of nullable tiles for intermediate steps.
     */
    fun applyGravity(rows: Int, cols: Int, currentTiles: Map<BoardPosition, FoodTile?>): Map<BoardPosition, FoodTile?> {
        val newTiles = mutableMapOf<BoardPosition, FoodTile?>()

        for (c in 0 until cols) {
            val columnTiles = mutableListOf<FoodTile>()
            // Collect all existing tiles in this column from bottom to top
            for (r in rows - 1 downTo 0) {
                val tile = currentTiles[BoardPosition(r, c)]
                if (tile != null) {
                    columnTiles.add(tile)
                }
            }

            // Place them back from bottom to top
            for (r in rows - 1 downTo 0) {
                val targetPos = BoardPosition(r, c)
                val tileIndex = (rows - 1) - r
                if (tileIndex < columnTiles.size) {
                    newTiles[targetPos] = columnTiles[tileIndex]
                } else {
                    newTiles[targetPos] = null
                }
            }
        }

        return newTiles
    }
}
