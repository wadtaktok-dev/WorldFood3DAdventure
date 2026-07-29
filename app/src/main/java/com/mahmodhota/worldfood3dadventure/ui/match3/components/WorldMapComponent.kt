package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.progress.ProgressionManager
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import kotlin.math.atan2

@Composable
fun WorldMapComponent(
    mapScale: Float,
    offset: Offset,
    state: TransformableState,
    selectedCountryId: String?,
    onCountryClick: (String) -> Unit,
    onCountryLongClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val progressMap = ProgressionManager.progressMap
    val mapCoords = WorldMapGeometry.mapCoords

    Box(
        modifier = modifier
            .fillMaxSize()
            .transformable(state = state)
            .background(PremiumColors.DeepNavy)
    ) {
        OceanLayer()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = mapScale,
                    scaleY = mapScale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        ) {
            IllustratedContinents()

            // Routes
            TravelPathComposable(start = mapCoords["germany"] ?: Offset.Zero, end = mapCoords["italy"] ?: Offset.Zero, isUnlocked = progressMap["italy"]?.isUnlocked == true)
            TravelPathComposable(start = mapCoords["italy"] ?: Offset.Zero, end = mapCoords["france"] ?: Offset.Zero, isUnlocked = progressMap["france"]?.isUnlocked == true)
            
            if (progressMap["italy"]?.isUnlocked == true) {
                val gPos = mapCoords["germany"] ?: Offset.Zero
                val iPos = mapCoords["italy"] ?: Offset.Zero
                PlaneAnimationV2(start = gPos, end = iPos)
            }

            LevelRegistry.allCountries.forEach { country ->
                val coords = mapCoords[country.levelId] ?: Offset.Zero
                val progress = progressMap[country.levelId] ?: com.mahmodhota.worldfood3dadventure.game.progress.CountryProgress(country.levelId)
                
                CountryNodeComposable(
                    country = country,
                    progress = progress,
                    isSelected = selectedCountryId == country.levelId,
                    onClick = { onCountryClick(country.levelId) },
                    onLongClick = { onCountryLongClick(country.levelId) },
                    modifier = Modifier.offset(coords.x.dp, coords.y.dp)
                )
            }
        }

        CloudsLayerV2()
        VignetteLayer()
    }
}

@Composable
private fun OceanLayer() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.radialGradient(
                colors = listOf(Color(0xFF1B263B), Color(0xFF0D1B2A)),
                radius = 2000f
            )
        )
    )
}

@Composable
private fun IllustratedContinents() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val landGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF4CAF50), Color(0xFF81C784), Color(0xFFC8E6C9))
        )
        val desertGradient = Brush.linearGradient(
            colors = listOf(Color(0xFFFBC02D), Color(0xFFFFF176))
        )
        val shadowColor = Color.Black.copy(alpha = 0.2f)

        // Draw continents from Geometry
        drawLandmass(WorldMapGeometry.createNorthAmerica(), landGradient, shadowColor)
        drawLandmass(WorldMapGeometry.createSouthAmerica(), landGradient, shadowColor)
        drawLandmass(WorldMapGeometry.createEurope(), landGradient, shadowColor)
        drawLandmass(WorldMapGeometry.createAfrica(), desertGradient, shadowColor)
        drawLandmass(WorldMapGeometry.createAsia(), landGradient, shadowColor)
        drawLandmass(WorldMapGeometry.createOceania(), landGradient, shadowColor)
        drawLandmass(WorldMapGeometry.createAntarctica(), Brush.linearGradient(listOf(Color.White, Color(0xFFE3F2FD))), shadowColor)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawLandmass(path: Path, brush: Brush, shadow: Color) {
    translate(4f, 4f) {
        drawPath(path, shadow)
    }
    drawPath(path, brush)
}

@Composable
private fun PlaneAnimationV2(start: Offset, end: Offset) {
    val infiniteTransition = rememberInfiniteTransition(label = "plane")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "planeProgress"
    )

    val mid = Offset((start.x + end.x) / 2, (start.y + end.y) / 2)
    val dx = end.x - start.x
    val dy = end.y - start.y
    val cp = Offset(mid.x - dy * 0.25f, mid.y + dx * 0.25f)

    val t = progress
    val x = (1 - t) * (1 - t) * start.x + 2 * (1 - t) * t * cp.x + t * t * end.x
    val y = (1 - t) * (1 - t) * start.y + 2 * (1 - t) * t * cp.y + t * t * end.y

    val tx = 2 * (1 - t) * (cp.x - start.x) + 2 * t * (end.x - cp.x)
    val ty = 2 * (1 - t) * (cp.y - start.y) + 2 * t * (end.y - cp.y)
    val angle = Math.toDegrees(atan2(ty, tx).toDouble()).toFloat()

    Box(
        modifier = Modifier
            .offset(x.dp, y.dp)
            .size(24.dp)
            .graphicsLayer { rotationZ = angle + 90f },
        contentAlignment = Alignment.Center
    ) {
        Text("✈️", fontSize = 16.sp)
    }
}

@Composable
private fun CloudsLayerV2() {
    val infiniteTransition = rememberInfiniteTransition(label = "clouds")
    val cloudOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(120000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cloudOffset"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        repeat(6) { i ->
            val yPos = (50 + i * 180).dp
            val xPos = ((cloudOffset + i * 450) % 1200 - 300).dp
            val size = (140 + (i % 2) * 80).dp
            
            Box(
                modifier = Modifier
                    .size(size, size / 3)
                    .offset(x = xPos, y = yPos)
                    .alpha(0.1f)
                    .clip(RoundedCornerShape(size))
                    .background(Color.White)
            )
        }
    }
}

@Composable
private fun VignetteLayer() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.radialGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f)),
                radius = 1600f
            )
        )
    )
}
