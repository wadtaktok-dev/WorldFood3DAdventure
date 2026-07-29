package com.mahmodhota.worldfood3dadventure.game.match3

import com.mahmodhota.worldfood3dadventure.game.match3.engine.Match3Engine
import com.mahmodhota.worldfood3dadventure.game.match3.model.*
import org.junit.Assert.*
import org.junit.Test

class Match3EngineTest {

    @Test
    fun testSuccessfulSwap() {
        val engine = Match3Engine(seed = 123)
        val board = engine.createStartBoard()
        
        // Find a valid move
        val validMoves = com.mahmodhota.worldfood3dadventure.game.match3.engine.MoveFinder.findValidMoves(board)
        assertTrue(validMoves.isNotEmpty())
        
        val move = validMoves[0]
        val result = engine.performSwap(board, move.first, move.second)
        
        assertTrue(result is SwapResult.Success)
        val success = result as SwapResult.Success
        assertNotEquals(board, success.stableBoard)
        assertTrue(success.scoreGained > 0)
    }

    @Test
    fun testInvalidSwap() {
        val engine = Match3Engine(seed = 123)
        val board = engine.createStartBoard()
        
        // Attempt to swap distant tiles
        val result = engine.performSwap(board, BoardPosition(0, 0), BoardPosition(5, 5))
        
        assertTrue(result is SwapResult.NotAdjacent)
    }
}
