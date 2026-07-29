package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.*

/**
 * Result of the entire cascade sequence.
 */
data class CascadeResult(
    val finalBoard: Match3Board,
    val steps: List<MatchResult>,
    val totalScore: Int,
    val totalMatchedTiles: Int,
    val collectedCounts: Map<FoodTileType, Int>
)

/**
 * Processes recursive matches, gravity, and refills until the board is stable.
 */
class CascadeProcessor(
    private val refillEngine: RefillEngine,
    private val maxCascades: Int = 20
) {

    /**
     * Resolves the board iteratively.
     */
    fun process(initialBoard: Match3Board): CascadeResult {
        val steps = mutableListOf<MatchResult>()
        var currentBoard = initialBoard
        var cascadeIndex = 1
        var totalScore = 0
        var totalMatched = 0
        val collected = mutableMapOf<FoodTileType, Int>()

        while (cascadeIndex <= maxCascades) {
            val matchResult = MatchDetector.findMatches(currentBoard)
            if (!matchResult.hasMatches) break

            // Expand clearance based on special tiles
            val totalClearedPositions = calculateTotalClearance(currentBoard, matchResult.uniquePositions)
            
            val stepMatchResult = matchResult.copy(uniquePositions = totalClearedPositions)
            steps.add(stepMatchResult)
            
            totalMatched += stepMatchResult.totalMatchedTiles
            totalScore += calculateScore(stepMatchResult.totalMatchedTiles, cascadeIndex)
            
            // Track collected types (Only from original matches for now, or all?)
            // Usually all cleared tiles count towards collection if they are valid food types.
            totalClearedPositions.forEach { pos ->
                currentBoard.tileAt(pos)?.let { tile ->
                    collected[tile.type] = (collected[tile.type] ?: 0) + 1
                }
            }

            // 1. Remove matched tiles (conceptually set to null)
            val intermediateMap = mutableMapOf<BoardPosition, FoodTile?>()
            for (r in 0 until currentBoard.rows) {
                for (c in 0 until currentBoard.columns) {
                    val pos = BoardPosition(r, c)
                    if (totalClearedPositions.contains(pos)) {
                        intermediateMap[pos] = null
                    } else {
                        intermediateMap[pos] = currentBoard.tileAt(pos)
                    }
                }
            }
            
            // 2. Spawn Special Tiles
            matchResult.specialTilesToSpawn.forEach { (pos, type) ->
                // Create a special tile of the matched type? 
                // Or a dedicated special type? The SpecialTileType is an enum property of FoodTile.
                val originalTile = currentBoard.tileAt(pos)
                if (originalTile != null) {
                    intermediateMap[pos] = originalTile.copy(specialType = type)
                }
            }

            // 3. Apply Gravity
            val shiftedMap = GravitySolver.applyGravity(currentBoard.rows, currentBoard.columns, intermediateMap)

            // 4. Refill
            val fullMap = refillEngine.refill(currentBoard.rows, currentBoard.columns, shiftedMap)

            // 5. Update Board
            val tileList = mutableListOf<FoodTile>()
            for (r in 0 until currentBoard.rows) {
                for (c in 0 until currentBoard.columns) {
                    tileList.add(fullMap[BoardPosition(r, c)]!!)
                }
            }
            currentBoard = Match3Board(currentBoard.rows, currentBoard.columns, tileList)

            cascadeIndex++
        }

        return CascadeResult(currentBoard, steps, totalScore, totalMatched, collected)
    }

    private fun calculateScore(matchedCount: Int, cascadeMultiplier: Int): Int {
        val baseScore = 100
        return matchedCount * baseScore * cascadeMultiplier
    }

    private fun calculateTotalClearance(board: Match3Board, matchedPositions: Set<BoardPosition>): Set<BoardPosition> {
        val totalCleared = matchedPositions.toMutableSet()
        val toProcess = matchedPositions.toMutableList()
        val processedSpecials = mutableSetOf<BoardPosition>()

        var index = 0
        while (index < toProcess.size) {
            val pos = toProcess[index++]
            val tile = board.tileAt(pos) ?: continue

            if (tile.specialType != SpecialTileType.NONE && pos !in processedSpecials) {
                processedSpecials.add(pos)
                val effectArea = getSpecialEffectArea(board, pos, tile)
                effectArea.forEach { effectPos ->
                    if (totalCleared.add(effectPos)) {
                        toProcess.add(effectPos)
                    }
                }
            }
        }
        return totalCleared
    }

    private fun getSpecialEffectArea(board: Match3Board, pos: BoardPosition, tile: FoodTile): Set<BoardPosition> {
        return when (tile.specialType) {
            SpecialTileType.ROW_CLEAR -> {
                (0 until board.columns).map { BoardPosition(pos.row, it) }.toSet()
            }
            SpecialTileType.COLUMN_CLEAR -> {
                (0 until board.rows).map { BoardPosition(it, pos.column) }.toSet()
            }
            SpecialTileType.BOMB -> {
                val area = mutableSetOf<BoardPosition>()
                for (r in pos.row - 1..pos.row + 1) {
                    for (c in pos.column - 1..pos.column + 1) {
                        val p = BoardPosition(r, c)
                        if (board.contains(p)) area.add(p)
                    }
                }
                area
            }
            SpecialTileType.COLOR_BOMB -> {
                // For activation via match, clear all of the type of tile it was matched with?
                // Actually, Color Bombs are usually triggered by swapping with a tile.
                // If it's part of a match, we clear all tiles of the most common neighbor?
                // For now, let's say it clears all of the same FoodTileType as the tile itself (which is impossible for Color Bomb as it has a type).
                // Actually, FoodTileType is a required field.
                // Let's assume Color Bomb activation clears all tiles of its own FoodTileType on the board.
                board.allPositions().filter { board.tileAt(it)?.type == tile.type }.toSet()
            }
            else -> emptySet()
        }
    }
}
