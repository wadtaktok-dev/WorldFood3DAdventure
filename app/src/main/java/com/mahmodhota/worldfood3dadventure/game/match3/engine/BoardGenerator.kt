package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTile
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTileType
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3Board
import kotlin.random.Random

/**
 * Generates initial Match-3 boards with specific constraints.
 */
class BoardGenerator(
    private val random: Random = Random.Default,
    private val maxAttempts: Int = 1000
) {
    private var nextTileId: Long = 1L

    /**
     * Generates a fully populated board with no initial matches and at least one valid move.
     */
    fun generate(
        rows: Int = 8,
        cols: Int = 8,
        allowedTiles: List<FoodTileType> = FoodTileType.values().toList()
    ): Match3Board {
        repeat(maxAttempts) {
            val board = generatePotentialBoard(rows, cols, allowedTiles)
            if (!MatchDetector.findMatches(board).hasMatches && MoveFinder.hasValidMove(board)) {
                return board
            }
        }
        throw IllegalStateException("Failed to generate a valid board after $maxAttempts attempts")
    }

    private fun generatePotentialBoard(rows: Int, cols: Int, allowedTiles: List<FoodTileType>): Match3Board {
        val tiles = mutableListOf<FoodTile>()
        for (i in 0 until rows * cols) {
            tiles.add(createRandomTile(allowedTiles))
        }
        return Match3Board(rows, cols, tiles)
    }

    private fun createRandomTile(allowedTiles: List<FoodTileType>): FoodTile {
        val type = allowedTiles[random.nextInt(allowedTiles.size)]
        return FoodTile(id = nextTileId++, type = type)
    }

    /**
     * Resets the tile ID counter.
     */
    fun resetIdCounter() {
        nextTileId = 1L
    }
}
