package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTileType
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3Board
import com.mahmodhota.worldfood3dadventure.game.match3.model.SwapResult
import kotlin.random.Random

/**
 * Main coordinator for the Match-3 logic.
 */
class Match3Engine(
    seed: Long = Random.nextLong(),
    val allowedTiles: List<FoodTileType> = FoodTileType.values().toList()
) {
    private val random = Random(seed)
    private val refillEngine = RefillEngine(random, allowedTiles)
    private val cascadeProcessor = CascadeProcessor(refillEngine)
    private val generator = BoardGenerator(random)

    /**
     * Generates a new starting board.
     */
    fun createStartBoard(rows: Int = 8, cols: Int = 8): Match3Board {
        return generator.generate(rows, cols, allowedTiles)
    }

    /**
     * Performs a swap and returns the outcome.
     */
    fun performSwap(board: Match3Board, pos1: BoardPosition, pos2: BoardPosition): SwapResult {
        if (!board.contains(pos1) || !board.contains(pos2)) {
            return SwapResult.OutOfBounds
        }

        if (!pos1.isAdjacent(pos2)) {
            return SwapResult.NotAdjacent
        }

        val swappedBoard = board.swap(pos1, pos2)
        val initialMatches = MatchDetector.findMatches(swappedBoard)

        if (!initialMatches.hasMatches) {
            return SwapResult.NoMatch
        }

        // Process all cascades
        val cascadeResult = cascadeProcessor.process(swappedBoard)

        return SwapResult.Success(
            initialBoard = board,
            swappedBoard = swappedBoard,
            stableBoard = cascadeResult.finalBoard,
            cascadeSteps = cascadeResult.steps,
            scoreGained = cascadeResult.totalScore,
            totalMatchedTiles = cascadeResult.totalMatchedTiles,
            collectedCounts = cascadeResult.collectedCounts
        )
    }
}
