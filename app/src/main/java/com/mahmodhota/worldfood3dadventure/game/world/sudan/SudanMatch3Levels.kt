package com.mahmodhota.worldfood3dadventure.game.world.sudan

import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTileType
import com.mahmodhota.worldfood3dadventure.game.match3.model.LevelGoal
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3LevelDefinition

/**
 * 10 Playable Match-3 levels for the Sudan chapter.
 */
object SudanMatch3Levels {
    
    val levels = listOf(
        // Level 1: Kisra
        Match3LevelDefinition(
            levelNumber = 1,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.KISRA, FoodTileType.MULAH, FoodTileType.TAGALIA, FoodTileType.TOMATO),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.KISRA, 20)),
            moves = 25
        ),
        // Level 2: Ful Medames
        Match3LevelDefinition(
            levelNumber = 2,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.FUL_MEDAMES, FoodTileType.KISRA, FoodTileType.CHEESE, FoodTileType.TOMATO),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.FUL_MEDAMES, 15)),
            moves = 20
        ),
        // Level 3: Mulah
        Match3LevelDefinition(
            levelNumber = 3,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.MULAH, FoodTileType.KISRA, FoodTileType.BASIL, FoodTileType.TOMATO),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.MULAH, 20)),
            moves = 25
        ),
        // Level 4: Tagalia
        Match3LevelDefinition(
            levelNumber = 4,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.TAGALIA, FoodTileType.KISRA, FoodTileType.TOMATO, FoodTileType.BASIL),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.TAGALIA, 18)),
            moves = 24
        ),
        // Level 5: Agashe
        Match3LevelDefinition(
            levelNumber = 5,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.AGASHE, FoodTileType.SHAWAYA, FoodTileType.SAMBUSA, FoodTileType.TOMATO),
            goals = listOf(LevelGoal.ScoreTarget(7500)),
            moves = 26
        ),
        // Level 6: Sambusa
        Match3LevelDefinition(
            levelNumber = 6,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.SAMBUSA, FoodTileType.AGASHE, FoodTileType.KISRA, FoodTileType.CHEESE),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.SAMBUSA, 25)),
            moves = 28
        ),
        // Level 7: Shawaya
        Match3LevelDefinition(
            levelNumber = 7,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.SHAWAYA, FoodTileType.AGASHE, FoodTileType.GURRASA, FoodTileType.TOMATO),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.SHAWAYA, 15)),
            moves = 28
        ),
        // Level 8: Gurrasa
        Match3LevelDefinition(
            levelNumber = 8,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.GURRASA, FoodTileType.TAGALIA, FoodTileType.KISRA, FoodTileType.CHEESE),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.GURRASA, 20)),
            moves = 30
        ),
        // Level 9: Asida
        Match3LevelDefinition(
            levelNumber = 9,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.ASIDA, FoodTileType.MULAH, FoodTileType.KISRA, FoodTileType.TOMATO),
            goals = listOf(LevelGoal.CollectFood(FoodTileType.ASIDA, 15)),
            moves = 30
        ),
        // Level 10: Sudan Master
        Match3LevelDefinition(
            levelNumber = 10,
            countryId = "sudan",
            allowedTiles = listOf(FoodTileType.KISRA, FoodTileType.FUL_MEDAMES, FoodTileType.MULAH, FoodTileType.AGASHE, FoodTileType.SAMBUSA, FoodTileType.ASIDA),
            goals = listOf(LevelGoal.ScoreTarget(20000)),
            moves = 35
        )
    )

    fun getLevel(levelNumber: Int): Match3LevelDefinition? {
        return levels.find { it.levelNumber == levelNumber }
    }
}
