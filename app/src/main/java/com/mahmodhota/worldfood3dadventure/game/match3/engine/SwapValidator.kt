package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3Board

/**
 * Validates player swap requests.
 */
object SwapValidator {

    /**
     * Checks if a swap is legal and results in a match.
     */
    fun isValid(board: Match3Board, pos1: BoardPosition, pos2: BoardPosition): Boolean {
        if (!board.contains(pos1) || !board.contains(pos2)) return false
        if (!pos1.isAdjacent(pos2)) return false
        
        val swapped = board.swap(pos1, pos2)
        return MatchDetector.findMatches(swapped).hasMatches
    }
}
