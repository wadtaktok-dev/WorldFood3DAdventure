package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

/**
 * Geometric definitions for the Premium Adventure Map V2.
 * Using a strict 1000x500 logical coordinate system.
 */
object WorldMapGeometry {

    const val MAP_WIDTH = 1000f
    const val MAP_HEIGHT = 500f

    val mapCoords = mapOf(
        "germany" to Offset(515f, 120f),
        "italy" to Offset(530f, 180f),
        "france" to Offset(480f, 150f),
        "sudan" to Offset(560f, 320f),
        "japan" to Offset(880f, 160f),
        "mexico" to Offset(180f, 240f)
    )

    // Terrain Landmarks (Centers for features)
    val mountainRanges = listOf(
        Offset(200f, 100f), // Rockies
        Offset(350f, 400f), // Andes
        Offset(520f, 100f), // Alps
        Offset(750f, 150f), // Himalayas
        Offset(550f, 350f)  // Atlas
    )

    val forestZones = listOf(
        Offset(150f, 150f), // NW Forests
        Offset(380f, 350f), // Amazon
        Offset(550f, 420f), // Congo
        Offset(850f, 250f)  // SE Asia
    )

    fun createNorthAmerica(): Path = Path().apply {
        moveTo(50f, 50f)
        quadraticTo(150f, 30f, 350f, 40f)
        lineTo(380f, 150f)
        quadraticTo(350f, 220f, 280f, 280f)
        quadraticTo(200f, 320f, 150f, 320f)
        quadraticTo(80f, 250f, 60f, 150f)
        close()
    }

    fun createSouthAmerica(): Path = Path().apply {
        moveTo(280f, 280f)
        quadraticTo(380f, 280f, 420f, 350f)
        quadraticTo(400f, 450f, 350f, 480f)
        lineTo(280f, 480f)
        quadraticTo(250f, 400f, 280f, 280f)
        close()
    }

    fun createEurope(): Path = Path().apply {
        moveTo(430f, 40f)
        quadraticTo(520f, 30f, 600f, 40f)
        lineTo(620f, 120f)
        quadraticTo(580f, 180f, 440f, 180f)
        quadraticTo(410f, 120f, 430f, 40f)
        close()
    }

    fun createAfrica(): Path = Path().apply {
        moveTo(440f, 180f)
        quadraticTo(650f, 180f, 680f, 300f)
        quadraticTo(620f, 450f, 550f, 480f)
        lineTo(450f, 480f)
        quadraticTo(410f, 350f, 440f, 180f)
        close()
    }

    fun createAsia(): Path = Path().apply {
        moveTo(600f, 40f)
        quadraticTo(850f, 30f, 950f, 40f)
        lineTo(980f, 250f)
        quadraticTo(850f, 350f, 700f, 420f)
        quadraticTo(620f, 350f, 620f, 120f)
        close()
    }

    fun createAustralia(): Path = Path().apply {
        moveTo(820f, 380f)
        quadraticTo(950f, 380f, 960f, 450f)
        quadraticTo(880f, 480f, 810f, 460f)
        close()
    }
}
