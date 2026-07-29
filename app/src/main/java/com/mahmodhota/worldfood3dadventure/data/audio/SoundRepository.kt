package com.mahmodhota.worldfood3dadventure.data.audio

/**
 * Categorizes all game sound effects.
 */
enum class SfxType {
    BUTTON_CLICK,
    TILE_SELECT,
    SWAP_VALID,
    SWAP_INVALID,
    MATCH_SMALL,
    MATCH_LARGE,
    COMBO_1,
    COMBO_2,
    COMBO_3,
    CASCADE,
    COIN_COLLECT,
    STAR_EARNED,
    XP_GAINED,
    LEVEL_UNLOCK,
    COUNTRY_UNLOCK,
    VICTORY,
    DEFEAT,
    ITALY_VICTORY,
    JAPAN_VICTORY,
    MEXICO_VICTORY
}

/**
 * Categorizes background music tracks.
 */
enum class MusicType {
    WORLD_MAP,
    GERMANY_THEME,
    ITALY_THEME,
    FRANCE_THEME,
    JAPAN_THEME,
    MEXICO_THEME,
    NONE
}

/**
 * Maps sound types to placeholder resource IDs.
 * In a real production environment, these would point to R.raw.sound_file.
 */
object SoundRepository {
    
    // SFX mapping (Placeholders)
    val sfxMap = mapOf(
        SfxType.BUTTON_CLICK to 1,
        SfxType.TILE_SELECT to 2,
        SfxType.SWAP_VALID to 3,
        SfxType.SWAP_INVALID to 4,
        SfxType.MATCH_SMALL to 5,
        SfxType.MATCH_LARGE to 6,
        SfxType.VICTORY to 7,
        SfxType.DEFEAT to 8,
        SfxType.ITALY_VICTORY to 9,
        SfxType.JAPAN_VICTORY to 10,
        SfxType.MEXICO_VICTORY to 11
    )

    // Music mapping (Placeholders)
    val musicMap = mapOf(
        MusicType.WORLD_MAP to 101,
        MusicType.GERMANY_THEME to 102,
        MusicType.ITALY_THEME to 103,
        MusicType.FRANCE_THEME to 104,
        MusicType.JAPAN_THEME to 105,
        MusicType.MEXICO_THEME to 106
    )

    fun getMusicForCountry(countryId: String): MusicType {
        return when (countryId.lowercase()) {
            "germany" -> MusicType.GERMANY_THEME
            "italy" -> MusicType.ITALY_THEME
            "france" -> MusicType.FRANCE_THEME
            "japan" -> MusicType.JAPAN_THEME
            "mexico" -> MusicType.MEXICO_THEME
            else -> MusicType.WORLD_MAP
        }
    }
}
