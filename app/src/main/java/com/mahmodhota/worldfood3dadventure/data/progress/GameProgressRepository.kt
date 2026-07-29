package com.mahmodhota.worldfood3dadventure.data.progress

import android.content.Context
import androidx.datastore.preferences.core.*
import com.mahmodhota.worldfood3dadventure.data.progress.model.*
import com.mahmodhota.worldfood3dadventure.game.match3.Match3LevelRegistry
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Single source of truth for persisted game state.
 */
class GameProgressRepository(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _state = MutableStateFlow(PersistedGameState())
    val state: StateFlow<PersistedGameState> = _state.asStateFlow()

    init {
        scope.launch {
            context.gameDataStore.data
                .catch { emit(emptyPreferences()) }
                .map { prefs -> mapToGameState(prefs) }
                .collect { _state.value = it }
        }
    }

    private fun mapToGameState(prefs: Preferences): PersistedGameState {
        val player = PlayerProgress(
            lives = prefs[GameKeys.LIVES] ?: 5,
            coins = prefs[GameKeys.COINS] ?: 100,
            xp = prefs[GameKeys.XP] ?: 0,
            level = prefs[GameKeys.LEVEL] ?: 1,
            totalStars = prefs[GameKeys.TOTAL_STARS] ?: 0,
            username = prefs[GameKeys.USERNAME] ?: "Traveler"
        )

        val countries = mutableMapOf<String, CountryGameProgress>()
        LevelRegistry.allCountryIds.forEach { id ->
            val unlocked = prefs[GameKeys.countryUnlocked(id)] ?: (id == "germany" || id == "italy")
            val countryDef = LevelRegistry.getCountry(id)
            val levels = countryDef?.levels?.associate { levelDef ->
                val lvl = levelDef.levelNumber
                lvl to LevelProgress(
                    levelNumber = lvl,
                    countryId = id,
                    isUnlocked = prefs[GameKeys.levelUnlocked(id, lvl)] ?: (unlocked && lvl == 1),
                    isCompleted = prefs[GameKeys.levelCompleted(id, lvl)] ?: false,
                    bestStars = prefs[GameKeys.levelStars(id, lvl)] ?: 0,
                    bestScore = prefs[GameKeys.levelScore(id, lvl)] ?: 0
                )
            } ?: emptyMap()
            
            countries[id] = CountryGameProgress(
                countryId = id,
                isUnlocked = unlocked,
                isCompleted = prefs[GameKeys.countryCompleted(id)] ?: false,
                levels = levels
            )
        }

        return PersistedGameState(
            player = player,
            countries = countries,
            lastSelectedCountry = prefs[GameKeys.LAST_COUNTRY] ?: "germany",
            lastSelectedLevel = prefs[GameKeys.LAST_LEVEL] ?: 1,
            isChapter1Completed = prefs[GameKeys.CHAPTER_1_COMPLETED] ?: false,
            worldExplorerBadge = prefs[GameKeys.WORLD_EXPLORER_BADGE] ?: false,
            schemaVersion = prefs[GameKeys.SCHEMA_VERSION] ?: 1
        )
    }

    suspend fun saveLevelProgress(
        countryId: String,
        levelNumber: Int,
        stars: Int,
        score: Int,
        xpReward: Int,
        coinReward: Int
    ) {
        context.gameDataStore.edit { prefs ->
            val currentBestStars = prefs[GameKeys.levelStars(countryId, levelNumber)] ?: 0
            val currentBestScore = prefs[GameKeys.levelScore(countryId, levelNumber)] ?: 0
            val isFirstTime = !(prefs[GameKeys.levelCompleted(countryId, levelNumber)] ?: false)

            prefs[GameKeys.levelCompleted(countryId, levelNumber)] = true
            
            if (stars > currentBestStars) {
                val diff = stars - currentBestStars
                prefs[GameKeys.TOTAL_STARS] = (prefs[GameKeys.TOTAL_STARS] ?: 0) + diff
                prefs[GameKeys.levelStars(countryId, levelNumber)] = stars
            }
            
            if (score > currentBestScore) {
                prefs[GameKeys.levelScore(countryId, levelNumber)] = score
            }

            if (isFirstTime) {
                prefs[GameKeys.XP] = (prefs[GameKeys.XP] ?: 0) + xpReward
                prefs[GameKeys.COINS] = (prefs[GameKeys.COINS] ?: 100) + coinReward
                
                val hasNextLevel = Match3LevelRegistry.getLevel(countryId, levelNumber + 1) != null
                if (hasNextLevel) {
                    prefs[GameKeys.levelUnlocked(countryId, levelNumber + 1)] = true
                } else {
                    // Unlock next country in the chain
                    val levelChain = LevelRegistry.allCountryIds
                    val nextIndex = levelChain.indexOf(countryId) + 1
                    if (nextIndex in levelChain.indices) {
                        val nextId = levelChain[nextIndex]
                        prefs[GameKeys.countryUnlocked(nextId)] = true
                        prefs[GameKeys.levelUnlocked(nextId, 1)] = true
                    } else if (countryId == "sudan") {
                        // Chapter 1 Finale
                        prefs[GameKeys.CHAPTER_1_COMPLETED] = true
                        prefs[GameKeys.WORLD_EXPLORER_BADGE] = true
                        prefs[GameKeys.COINS] = (prefs[GameKeys.COINS] ?: 100) + 500
                        prefs[GameKeys.XP] = (prefs[GameKeys.XP] ?: 0) + 1000
                    }
                }
            }
        }
    }

    suspend fun updatePlayerStats(lives: Int? = null, coins: Int? = null, xp: Int? = null) {
        context.gameDataStore.edit { prefs ->
            lives?.let { prefs[GameKeys.LIVES] = it }
            coins?.let { prefs[GameKeys.COINS] = it }
            xp?.let { prefs[GameKeys.XP] = it }
        }
    }

    suspend fun resetAllProgress() {
        context.gameDataStore.edit { it.clear() }
    }
}
