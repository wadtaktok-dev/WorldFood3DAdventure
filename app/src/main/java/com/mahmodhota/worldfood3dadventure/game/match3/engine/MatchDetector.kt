package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.*

/**
 * Scans the board for horizontal and vertical matches.
 */
object MatchDetector {

    /**
     * Finds all matches on the current board.
     */
    fun findMatches(board: Match3Board): MatchResult {
        val horizontalGroups = findHorizontalGroups(board)
        val verticalGroups = findVerticalGroups(board)

        val uniquePositions = mutableSetOf<BoardPosition>()
        uniquePositions.addAll(horizontalGroups.flatMap { it.positions })
        uniquePositions.addAll(verticalGroups.flatMap { it.positions })

        val finalGroups = mutableListOf<MatchGroup>()
        val specialSpawns = mutableMapOf<BoardPosition, SpecialTileType>()

        // 1. Process T/L Shapes (Intersections)
        // A T/L shape occurs when a position belongs to both a horizontal and vertical group of same type
        val processedHorizontal = mutableSetOf<MatchGroup>()
        val processedVertical = mutableSetOf<MatchGroup>()

        horizontalGroups.forEach { hGroup ->
            verticalGroups.forEach { vGroup ->
                if (hGroup.type == vGroup.type) {
                    val intersection = hGroup.positions.intersect(vGroup.positions)
                    if (intersection.isNotEmpty()) {
                        // T or L shape found
                        val combinedPositions = hGroup.positions + vGroup.positions
                        val spawnPoint = intersection.first()
                        finalGroups.add(MatchGroup(combinedPositions, MatchDirection.HORIZONTAL, hGroup.type, SpecialTileType.BOMB, spawnPoint))
                        specialSpawns[spawnPoint] = SpecialTileType.BOMB
                        processedHorizontal.add(hGroup)
                        processedVertical.add(vGroup)
                    }
                }
            }
        }

        // 2. Process Line Matches (5 and 4)
        horizontalGroups.filter { it !in processedHorizontal }.forEach { group ->
            val spawnType = when (group.length) {
                5 -> SpecialTileType.COLOR_BOMB
                4 -> SpecialTileType.COLUMN_CLEAR // Horizontal 4 creates Vertical clear
                else -> SpecialTileType.NONE
            }
            val spawnPoint = group.positions.first() // Default to first for now
            finalGroups.add(group.copy(creationType = spawnType, creationPoint = if (spawnType != SpecialTileType.NONE) spawnPoint else null))
            if (spawnType != SpecialTileType.NONE) specialSpawns[spawnPoint] = spawnType
        }

        verticalGroups.filter { it !in processedVertical }.forEach { group ->
            val spawnType = when (group.length) {
                5 -> SpecialTileType.COLOR_BOMB
                4 -> SpecialTileType.ROW_CLEAR // Vertical 4 creates Horizontal clear
                else -> SpecialTileType.NONE
            }
            val spawnPoint = group.positions.first()
            finalGroups.add(group.copy(creationType = spawnType, creationPoint = if (spawnType != SpecialTileType.NONE) spawnPoint else null))
            if (spawnType != SpecialTileType.NONE) specialSpawns[spawnPoint] = spawnType
        }

        return MatchResult(finalGroups, uniquePositions, specialSpawns)
    }

    private fun findHorizontalGroups(board: Match3Board): List<MatchGroup> {
        val groups = mutableListOf<MatchGroup>()
        for (r in 0 until board.rows) {
            var c = 0
            while (c < board.columns) {
                val startTile = board.tileAt(BoardPosition(r, c))
                if (startTile == null) {
                    c++
                    continue
                }

                var matchLength = 1
                while (c + matchLength < board.columns) {
                    val nextTile = board.tileAt(BoardPosition(r, c + matchLength))
                    if (nextTile != null && nextTile.type == startTile.type) {
                        matchLength++
                    } else {
                        break
                    }
                }

                if (matchLength >= 3) {
                    val positions = (0 until matchLength).map { BoardPosition(r, c + it) }.toSet()
                    groups.add(MatchGroup(positions, MatchDirection.HORIZONTAL, startTile.type))
                    c += matchLength
                } else {
                    c++
                }
            }
        }
        return groups
    }

    private fun findVerticalGroups(board: Match3Board): List<MatchGroup> {
        val groups = mutableListOf<MatchGroup>()
        for (c in 0 until board.columns) {
            var r = 0
            while (r < board.rows) {
                val startTile = board.tileAt(BoardPosition(r, c))
                if (startTile == null) {
                    r++
                    continue
                }

                var matchLength = 1
                while (r + matchLength < board.rows) {
                    val nextTile = board.tileAt(BoardPosition(r + matchLength, c))
                    if (nextTile != null && nextTile.type == startTile.type) {
                        matchLength++
                    } else {
                        break
                    }
                }

                if (matchLength >= 3) {
                    val positions = (0 until matchLength).map { BoardPosition(r + it, c) }.toSet()
                    groups.add(MatchGroup(positions, MatchDirection.VERTICAL, startTile.type))
                    r += matchLength
                } else {
                    r++
                }
            }
        }
        return groups
    }
}
