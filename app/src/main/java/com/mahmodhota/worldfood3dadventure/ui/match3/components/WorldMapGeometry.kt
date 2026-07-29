package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

/**
 * Geometric definitions for the illustrated world map.
 * Using a 1000x500 normalized coordinate system.
 */
object WorldMapGeometry {

    val mapCoords = mapOf(
        "germany" to Offset(495f, 130f),
        "italy" to Offset(505f, 175f),
        "france" to Offset(470f, 155f),
        "sudan" to Offset(560f, 280f),
        "japan" to Offset(880f, 160f),
        "mexico" to Offset(180f, 240f)
    )

    fun createNorthAmerica(): Path = Path().apply {
        moveTo(50f, 50f)
        lineTo(350f, 50f)
        lineTo(400f, 200f)
        lineTo(250f, 350f)
        lineTo(150f, 350f)
        lineTo(50f, 200f)
        close()
    }

    fun createSouthAmerica(): Path = Path().apply {
        moveTo(280f, 350f)
        lineTo(420f, 350f)
        lineTo(350f, 550f)
        lineTo(250f, 550f)
        close()
    }

    fun createEurope(): Path = Path().apply {
        moveTo(420f, 50f)
        lineTo(580f, 50f)
        lineTo(580f, 180f)
        lineTo(420f, 180f)
        close()
    }

    fun createAfrica(): Path = Path().apply {
        moveTo(420f, 180f)
        lineTo(650f, 180f)
        lineTo(680f, 350f)
        lineTo(550f, 500f)
        lineTo(420f, 400f)
        close()
    }

    fun createAsia(): Path = Path().apply {
        moveTo(580f, 50f)
        lineTo(950f, 50f)
        lineTo(980f, 300f)
        lineTo(700f, 450f)
        lineTo(580f, 200f)
        close()
    }

    fun createOceania(): Path = Path().apply {
        moveTo(800f, 400f)
        lineTo(950f, 400f)
        lineTo(950f, 520f)
        lineTo(800f, 520f)
        close()
    }

    fun createAntarctica(): Path = Path().apply {
        moveTo(50f, 580f)
        lineTo(950f, 580f)
        lineTo(950f, 620f)
        lineTo(50f, 620f)
        close()
    }
}
