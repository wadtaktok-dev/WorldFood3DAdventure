package com.mahmodhota.worldfood3dadventure.game.match3.engine

import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3Board

/**
 * Scans a stable board for possible valid swaps.
 */
object MoveFinder {

    /**
     * Returns true if at least one valid swap exists.
     */
    fun hasValidMove(board: Match3Board): Boolean {
        // Iterate through all possible horizontal swaps
        for (r in 0 until board.rows) {
            for (c in 0 until board.columns - 1) {
                val p1 = BoardPosition(r, c)
                val p2 = BoardPosition(r, c + 1)
                if (wouldCreateMatch(board, p1, p2)) return true
            }
        }

        // Iterate through all possible vertical swaps
        for (c in 0 until board.columns) {
            for (r in 0 until board.rows - 1) {
                val p1 = BoardPosition(r, c)
                val p2 = BoardPosition(r + 1, c)
                if (wouldCreateMatch(board, p1, p2)) return true
            }
        }

        return false
    }

    /**
     * Checks if swapping two tiles results in any match.
     */
    fun wouldCreateMatch(board: Match3Board, pos1: BoardPosition, pos2: BoardPosition): Boolean {
        val swapped = board.swap(pos1, pos2)
        return MatchDetector.findMatches(swapped).hasMatches
    }

    /**
     * Finds all valid moves on the board.
     */
    fun findValidMoves(board: Match3Board): List<Pair<BoardPosition, BoardPosition>> {
        val moves = mutableListOf<Pair<BoardPosition, BoardPosition>>()
        
        // Horizontal
        for (r in 0 until board.rows) {
            for (c in 0 until board.columns - 1) {
                val p1 = BoardPosition(r, c)
                val p2 = BoardPosition(r, c + 1)
                if (wouldCreateMatch(board, p1, p2)) moves.add(p1 to p2)
            }
        }

        // Vertical
        for (c in 0 until board.columns) {
            for (r in 0 until board.rows - 1) {
                val p1 = BoardPosition(r, c)
                val p2 = BoardPosition(r + 1, c)
                if (wouldCreateMatch(board, p1, p2)) moves.add(p1 to p2)
            }
        }

        return moves
    }
}
