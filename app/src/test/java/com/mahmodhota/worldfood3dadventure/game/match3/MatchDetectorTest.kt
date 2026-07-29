package com.mahmodhota.worldfood3dadventure.game.match3

import com.mahmodhota.worldfood3dadventure.game.match3.engine.MatchDetector
import com.mahmodhota.worldfood3dadventure.game.match3.model.*
import org.junit.Assert.*
import org.junit.Test

class MatchDetectorTest {

    private fun createTestBoard(grid: List<List<FoodTileType>>): Match3Board {
        val rows = grid.size
        val cols = grid[0].size
        val tiles = mutableListOf<FoodTile>()
        var id = 1L
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                tiles.add(FoodTile(id++, grid[r][c]))
            }
        }
        return Match3Board(rows, cols, tiles)
    }

    @Test
    fun testHorizontalMatch() {
        val grid = listOf(
            listOf(FoodTileType.PIZZA, FoodTileType.PIZZA, FoodTileType.PIZZA),
            listOf(FoodTileType.PASTA, FoodTileType.TOMATO, FoodTileType.CHEESE)
        )
        val board = createTestBoard(grid)
        val result = MatchDetector.findMatches(board)
        
        assertTrue(result.hasMatches)
        assertEquals(1, result.matchGroups.size)
        assertEquals(3, result.totalMatchedTiles)
        assertEquals(MatchDirection.HORIZONTAL, result.matchGroups[0].direction)
    }

    @Test
    fun testVerticalMatch() {
        val grid = listOf(
            listOf(FoodTileType.PIZZA, FoodTileType.PASTA),
            listOf(FoodTileType.PIZZA, FoodTileType.TOMATO),
            listOf(FoodTileType.PIZZA, FoodTileType.CHEESE)
        )
        val board = createTestBoard(grid)
        val result = MatchDetector.findMatches(board)
        
        assertTrue(result.hasMatches)
        assertEquals(1, result.matchGroups.size)
        assertEquals(3, result.totalMatchedTiles)
        assertEquals(MatchDirection.VERTICAL, result.matchGroups[0].direction)
    }

    @Test
    fun testNoMatch() {
        val grid = listOf(
            listOf(FoodTileType.PIZZA, FoodTileType.PASTA, FoodTileType.PIZZA),
            listOf(FoodTileType.PASTA, FoodTileType.PIZZA, FoodTileType.PASTA)
        )
        val board = createTestBoard(grid)
        val result = MatchDetector.findMatches(board)
        
        assertFalse(result.hasMatches)
    }

    @Test
    fun testCrossingMatch() {
        val grid = listOf(
            listOf(FoodTileType.PASTA, FoodTileType.PIZZA, FoodTileType.PASTA),
            listOf(FoodTileType.PIZZA, FoodTileType.PIZZA, FoodTileType.PIZZA),
            listOf(FoodTileType.PASTA, FoodTileType.PIZZA, FoodTileType.PASTA)
        )
        val board = createTestBoard(grid)
        val result = MatchDetector.findMatches(board)
        
        assertTrue(result.hasMatches)
        // Now crossing matches are combined into 1 T/L group
        assertEquals(1, result.matchGroups.size)
        // Crossing at (1,1). Total unique positions should be 5.
        assertEquals(5, result.totalMatchedTiles)
        // Should create a BOMB
        assertEquals(SpecialTileType.BOMB, result.matchGroups[0].creationType)
    }
}
