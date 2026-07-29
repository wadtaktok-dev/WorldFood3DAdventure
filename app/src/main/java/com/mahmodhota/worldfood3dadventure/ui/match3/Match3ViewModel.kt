package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmodhota.worldfood3dadventure.data.audio.GlobalSystemManager
import com.mahmodhota.worldfood3dadventure.data.audio.SfxType
import com.mahmodhota.worldfood3dadventure.game.match3.Match3LevelRegistry
import com.mahmodhota.worldfood3dadventure.game.match3.engine.Match3Engine
import com.mahmodhota.worldfood3dadventure.game.match3.model.*
import com.mahmodhota.worldfood3dadventure.game.progress.ProgressionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Detailed UI state for the Match-3 game, including animation tracking.
 */
data class Match3UiState(
    val board: Match3Board,
    val score: Int = 0,
    val movesRemaining: Int = 20,
    val collectedCounts: Map<FoodTileType, Int> = emptyMap(),
    val status: GameStatus = GameStatus.PLAYING,
    val selectedPosition: BoardPosition? = null,
    val isAnimating: Boolean = false,
    val matchedPositions: Set<BoardPosition> = emptySet(),
    val goals: List<LevelGoal> = emptyList(),
    val scoreThresholds: StarThresholds = StarThresholds(800, 1200, 1500),
    val comboCount: Int = 0
)

class Match3ViewModel(
    val countryId: String,
    val levelNumber: Int
) : ViewModel() {
    
    private val levelDefinition = Match3LevelRegistry.getLevel(countryId, levelNumber) 
        ?: throw IllegalArgumentException("Invalid level: $countryId $levelNumber")
    
    private val engine = Match3Engine(allowedTiles = levelDefinition.allowedTiles)
    private val audio = GlobalSystemManager.audio
    private val haptics = GlobalSystemManager.haptics
    
    var uiState by mutableStateOf(
        Match3UiState(
            board = engine.createStartBoard(),
            movesRemaining = levelDefinition.moves,
            goals = levelDefinition.goals,
            scoreThresholds = levelDefinition.scoreThresholds
        )
    )
        private set

    fun onTileSelected(position: BoardPosition) {
        if (uiState.isAnimating || uiState.status != GameStatus.PLAYING) return

        audio.playSfx(SfxType.TILE_SELECT)
        haptics.light()

        val selected = uiState.selectedPosition
        if (selected == null) {
            uiState = uiState.copy(selectedPosition = position)
        } else {
            if (selected == position) {
                uiState = uiState.copy(selectedPosition = null)
            } else if (selected.isAdjacent(position)) {
                performSwap(selected, position)
            } else {
                uiState = uiState.copy(selectedPosition = position)
            }
        }
    }

    private fun performSwap(pos1: BoardPosition, pos2: BoardPosition) {
        viewModelScope.launch {
            uiState = uiState.copy(isAnimating = true, selectedPosition = null)
            
            val result = engine.performSwap(uiState.board, pos1, pos2)
            
            when (result) {
                is SwapResult.Success -> {
                    audio.playSfx(SfxType.SWAP_VALID)
                    // 1. Swap Animation
                    uiState = uiState.copy(board = result.swappedBoard)
                    delay(300L)

                    // 2. Cascade Iteration
                    result.cascadeSteps.forEachIndexed { index, step ->
                        audio.playSfx(SfxType.MATCH_SMALL)
                        haptics.medium()
                        uiState = uiState.copy(
                            matchedPositions = step.uniquePositions,
                            comboCount = index + 1
                        )
                        delay(400L)
                        uiState = uiState.copy(matchedPositions = emptySet())
                        delay(200L)
                    }

                    // Update local collection map
                    val newCollected = uiState.collectedCounts.toMutableMap()
                    result.collectedCounts.forEach { (type, count) ->
                        newCollected[type] = (newCollected[type] ?: 0) + count
                    }

                    uiState = uiState.copy(
                        board = result.stableBoard,
                        score = uiState.score + result.scoreGained,
                        movesRemaining = uiState.movesRemaining - 1,
                        collectedCounts = newCollected,
                        isAnimating = false,
                        comboCount = 0
                    )
                    
                    checkGameStatus()
                }
                else -> {
                    audio.playSfx(SfxType.SWAP_INVALID)
                    uiState = uiState.copy(board = uiState.board.swap(pos1, pos2))
                    delay(300L)
                    uiState = uiState.copy(board = uiState.board.swap(pos1, pos2), isAnimating = false)
                }
            }
        }
    }

    private fun checkGameStatus() {
        val won = uiState.goals.all { goal ->
            when (goal) {
                is LevelGoal.ScoreTarget -> uiState.score >= goal.target
                is LevelGoal.CollectFood -> (uiState.collectedCounts[goal.type] ?: 0) >= goal.amount
            }
        }
        
        if (won) {
            val victorySfx = when (countryId) {
                "italy" -> SfxType.ITALY_VICTORY
                "japan" -> SfxType.JAPAN_VICTORY
                "mexico" -> SfxType.MEXICO_VICTORY
                else -> SfxType.VICTORY
            }
            audio.playSfx(victorySfx)
            haptics.heavy()
            uiState = uiState.copy(status = GameStatus.WON)
            // Update Progression
            val stars = when {
                uiState.score >= uiState.scoreThresholds.threeStars -> 3
                uiState.score >= uiState.scoreThresholds.twoStars -> 2
                else -> 1
            }
            ProgressionManager.completeLevel(countryId, levelNumber, uiState.score, stars)
        } else if (uiState.movesRemaining <= 0) {
            audio.playSfx(SfxType.DEFEAT)
            uiState = uiState.copy(status = GameStatus.LOST)
        }
    }

    fun resetGame() {
        uiState = Match3UiState(board = engine.createStartBoard())
    }
}
