package com.mahmodhota.worldfood3dadventure.game.match3

import com.mahmodhota.worldfood3dadventure.game.match3.engine.BoardGenerator
import com.mahmodhota.worldfood3dadventure.game.match3.engine.MatchDetector
import com.mahmodhota.worldfood3dadventure.game.match3.engine.MoveFinder
import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class BoardGeneratorTest {

    @Test
    fun testGenerationConstraints() {
        val generator = BoardGenerator(Random(123))
        val board = generator.generate(8, 8)
        
        assertEquals(8, board.rows)
        assertEquals(8, board.columns)
        
        // No initial matches
        assertFalse(MatchDetector.findMatches(board).hasMatches)
        
        // At least one valid move
        assertTrue(MoveFinder.hasValidMove(board))
    }

    @Test
    fun testDeterminism() {
        val seed = 456L
        val gen1 = BoardGenerator(Random(seed))
        val gen2 = BoardGenerator(Random(seed))
        
        val board1 = gen1.generate(5, 5)
        val board2 = gen2.generate(5, 5)
        
        board1.allPositions().forEach { pos ->
            assertEquals(board1.tileAt(pos)!!.type, board2.tileAt(pos)!!.type)
        }
    }

    @Test
    fun testStressTest() {
        val stressCount = 10000
        val generator = BoardGenerator(Random(789))
        val startTime = System.currentTimeMillis()
        
        repeat(stressCount) {
            val board = generator.generate(8, 8)
            assertNotNull(board)
            assertFalse(MatchDetector.findMatches(board).hasMatches)
            assertTrue(MoveFinder.hasValidMove(board))
        }
        
        val endTime = System.currentTimeMillis()
        println("Stress test generated $stressCount boards in ${endTime - startTime} ms")
    }
}
