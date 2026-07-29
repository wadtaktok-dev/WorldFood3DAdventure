package com.mahmodhota.worldfood3dadventure.game.match3

import com.mahmodhota.worldfood3dadventure.game.match3.engine.CascadeProcessor
import com.mahmodhota.worldfood3dadventure.game.match3.engine.MatchDetector
import com.mahmodhota.worldfood3dadventure.game.match3.engine.RefillEngine
import com.mahmodhota.worldfood3dadventure.game.match3.model.*
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class CascadeProcessorTest {

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
    fun testSimpleCascade() {
        val grid = listOf(
            listOf(FoodTileType.PIZZA, FoodTileType.PASTA, FoodTileType.TOMATO),
            listOf(FoodTileType.CHEESE, FoodTileType.CHEESE, FoodTileType.CHEESE)
        )
        val board = createTestBoard(grid)
        
        val refillEngine = RefillEngine(Random(1))
        val processor = CascadeProcessor(refillEngine)
        
        val result = processor.process(board)
        
        assertTrue(result.steps.isNotEmpty())
        assertEquals(3, result.totalMatchedTiles)
        assertFalse(MatchDetector.findMatches(result.finalBoard).hasMatches)
    }
}
