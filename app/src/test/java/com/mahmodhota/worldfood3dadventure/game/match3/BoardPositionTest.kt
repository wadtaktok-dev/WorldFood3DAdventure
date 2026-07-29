package com.mahmodhota.worldfood3dadventure.game.match3

import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import org.junit.Assert.*
import org.junit.Test

class BoardPositionTest {

    @Test
    fun testAdjacency() {
        val center = BoardPosition(4, 4)
        
        // Orthogonal
        assertTrue(center.isAdjacent(BoardPosition(3, 4)))
        assertTrue(center.isAdjacent(BoardPosition(5, 4)))
        assertTrue(center.isAdjacent(BoardPosition(4, 3)))
        assertTrue(center.isAdjacent(BoardPosition(4, 5)))
        
        // Diagonals
        assertFalse(center.isAdjacent(BoardPosition(3, 3)))
        assertFalse(center.isAdjacent(BoardPosition(5, 5)))
        
        // Self
        assertFalse(center.isAdjacent(BoardPosition(4, 4)))
        
        // Distant
        assertFalse(center.isAdjacent(BoardPosition(0, 0)))
    }
}
