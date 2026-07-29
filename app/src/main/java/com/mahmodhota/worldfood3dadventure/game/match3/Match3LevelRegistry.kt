package com.mahmodhota.worldfood3dadventure.game.match3

import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3LevelDefinition
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry

/**
 * Registry to look up Match-3 level definitions across all countries.
 */
object Match3LevelRegistry {
    
    /**
     * Resolves a level definition.
     */
    fun getLevel(countryId: String, levelNumber: Int): Match3LevelDefinition? {
        val country = LevelRegistry.getCountry(countryId) ?: return null
        return country.levels.find { it.levelNumber == levelNumber }
    }
}
