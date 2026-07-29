package com.mahmodhota.worldfood3dadventure.game.progress

import androidx.compose.runtime.mutableStateMapOf
import com.mahmodhota.worldfood3dadventure.data.progress.GameProgressManager
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Manages game progression logic. Bridges in-memory state with Persistence.
 */
object ProgressionManager {

    private val levelChain get() = LevelRegistry.allCountryIds
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _progressMap = mutableStateMapOf<String, CountryProgress>()

    /**
     * Observable map of country progress.
     */
    val progressMap: Map<String, CountryProgress> get() = _progressMap

    /**
     * Initializes the manager by observing the repository.
     */
    fun initialize() {
        scope.launch {
            GameProgressManager.repository.state.collect { gameState ->
                // Sync repository data to our observable Compose map
                gameState.countries.forEach { (id, countryData) ->
                    _progressMap[id] = CountryProgress(
                        levelId = id,
                        isUnlocked = countryData.isUnlocked,
                        isCompleted = countryData.isCompleted,
                        levels = countryData.levels.values.map { 
                            Match3LevelProgress(
                                levelNumber = it.levelNumber,
                                isUnlocked = it.isUnlocked,
                                isCompleted = it.isCompleted,
                                stars = it.bestStars,
                                highScore = it.bestScore
                            )
                        }
                    )
                }
            }
        }
    }

    /**
     * Returns the progress for a specific country.
     */
    fun getCountryProgress(levelId: String): CountryProgress {
        return _progressMap[levelId] ?: CountryProgress(levelId = levelId)
    }

    /**
     * Completes a Match-3 level and saves to persistence.
     */
    fun completeLevel(countryId: String, levelNumber: Int, score: Int, stars: Int) {
        scope.launch {
            GameProgressManager.repository.saveLevelProgress(
                countryId = countryId,
                levelNumber = levelNumber,
                stars = stars,
                score = score,
                xpReward = 50 * stars,
                coinReward = 10 * stars
            )
        }
    }

    private fun unlockNextCountry(currentId: String) {
        val currentIndex = levelChain.indexOf(currentId)
        if (currentIndex != -1 && currentIndex < levelChain.size - 1) {
            val nextId = levelChain[currentIndex + 1]
            val nextCountry = getCountryProgress(nextId)
            if (!nextCountry.isUnlocked) {
                val updatedLevels = nextCountry.levels.map { 
                    if (it.levelNumber == 1) it.copy(isUnlocked = true) else it 
                }
                _progressMap[nextId] = nextCountry.copy(isUnlocked = true, levels = updatedLevels)
            }
        }
    }

    /**
     * Legacy method for marking country completed (3D mode compatibility).
     */
    fun markLevelCompleted(levelId: String) {
        val country = getCountryProgress(levelId)
        _progressMap[levelId] = country.copy(isCompleted = true)
        unlockNextCountry(levelId)
    }
}
