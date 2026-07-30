package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.progress.ProgressionManager
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun WorldMapComponent(
    mapScale: Float,
    offset: Offset,
    state: androidx.compose.foundation.gestures.TransformableState,
    selectedCountryId: String?,
    onCountryClick: (String) -> Unit,
    onCountryLongClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val progressMap = ProgressionManager.progressMap
    val mapCoords = WorldMapGeometry.mapCoords
    val density = LocalDensity.current

    // Cache Geometry Paths for Performance
    val continentPaths = remember {
        mapOf(
            "na" to WorldMapGeometry.createNorthAmerica(),
            "sa" to WorldMapGeometry.createSouthAmerica(),
            "eu" to WorldMapGeometry.createEurope(),
            "af" to WorldMapGeometry.createAfrica(),
            "as" to WorldMapGeometry.createAsia(),
            "au" to WorldMapGeometry.createAustralia()
        )
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
            .background(PremiumColors.OceanDeep)
            .transformable(state = state)
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()

        if (widthPx > 0 && heightPx > 0) {
            // Authoritative Aspect-Fit Transform (1000x500)
            val baseScale = min(
                widthPx / WorldMapGeometry.MAP_WIDTH,
                heightPx / WorldMapGeometry.MAP_HEIGHT
            )
            val baseOffsetX = (widthPx - WorldMapGeometry.MAP_WIDTH * baseScale) / 2f
            val baseOffsetY = (heightPx - WorldMapGeometry.MAP_HEIGHT * baseScale) / 2f

            // 1. Static Layers (Ocean Texture & Base Terrain)
            OceanBackgroundLayer()

            // 2. Transformation Layer (User Pan/Zoom)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = mapScale
                        scaleY = mapScale
                        translationX = offset.x
                        translationY = offset.y
                        transformOrigin = TransformOrigin.Center
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    translate(baseOffsetX, baseOffsetY) {
                        scale(baseScale, pivot = Offset.Zero) {
                            // Layer 3-5: Continents, Coastline, Terrain Landmarks
                            drawPremiumContinents(continentPaths)
                            drawTerrainFeatures()
                            drawTravelRoutes(mapCoords, progressMap)
                        }
                    }
                }

                // Layer 8: Markers
                LevelRegistry.allCountries.forEach { country ->
                    val logicalPos = mapCoords[country.levelId] ?: Offset.Zero
                    val progress = progressMap[country.levelId] ?: com.mahmodhota.worldfood3dadventure.game.progress.CountryProgress(country.levelId)
                    
                    val screenX = logicalPos.x * baseScale + baseOffsetX
                    val screenY = logicalPos.y * baseScale + baseOffsetY

                    with(density) {
                        CountryNodeComposable(
                            country = country,
                            progress = progress,
                            isSelected = selectedCountryId == country.levelId,
                            onClick = { onCountryClick(country.levelId) },
                            onLongClick = { onCountryLongClick(country.levelId) },
                            modifier = Modifier.offset { 
                                IntOffset(
                                    (screenX - 22.dp.toPx()).roundToInt(),
                                    (screenY - 22.dp.toPx()).roundToInt()
                                ) 
                            }
                        )
                    }
                }

                // Layer 7: Airplane
                if (progressMap["italy"]?.isUnlocked == true) {
                    val gLog = mapCoords["germany"] ?: Offset.Zero
                    val iLog = mapCoords["italy"] ?: Offset.Zero
                    val gPx = Offset(gLog.x * baseScale + baseOffsetX, gLog.y * baseScale + baseOffsetY)
                    val iPx = Offset(iLog.x * baseScale + baseOffsetX, iLog.y * baseScale + baseOffsetY)
                    PlaneAnimationV2(start = gPx, end = iPx)
                }
            }
        }

        AtmosphereLayer()
    }
}

@Composable
private fun OceanBackgroundLayer() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Deep water to shallow water transition
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(PremiumColors.OceanMid, PremiumColors.OceanDeep),
                center = center,
                radius = size.maxDimension / 2
            )
        )
        // Subtle ocean depth details
        repeat(5) { i ->
            drawCircle(
                color = Color.White.copy(alpha = 0.03f),
                radius = (size.width * 0.1f * (i + 1)),
                center = Offset(size.width * 0.2f * i, size.height * 0.3f * i),
                style = Stroke(width = 2f)
            )
        }
    }
}

private fun DrawScope.drawPremiumContinents(paths: Map<String, Path>) {
    val landBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF4CAF50), Color(0xFF66BB6A))
    )
    
    paths.values.forEach { path ->
        // Drop Shadow
        translate(4f, 4f) {
            drawPath(path, PremiumColors.LandShadow)
        }
        // Coastal Highlight (Outer)
        drawPath(path, PremiumColors.CoastHighlight, style = Stroke(width = 6f))
        // Base Land
        drawPath(path, landBrush)
    }
}

private fun DrawScope.drawTerrainFeatures() {
    // Forests
    WorldMapGeometry.forestZones.forEach { center ->
        drawCircle(
            color = PremiumColors.TerrainForest,
            radius = 15f,
            center = center
        )
        drawCircle(
            color = PremiumColors.TerrainForest.copy(alpha = 0.6f),
            radius = 10f,
            center = center + Offset(8f, -5f)
        )
    }
    
    // Mountains
    WorldMapGeometry.mountainRanges.forEach { center ->
        val mountainPath = Path().apply {
            moveTo(center.x, center.y - 12f)
            lineTo(center.x - 10f, center.y + 8f)
            lineTo(center.x + 10f, center.y + 8f)
            close()
        }
        drawPath(mountainPath, PremiumColors.TerrainMountain)
    }
    
    // Deserts (Simplified Sahara/Middle East)
    drawRect(
        color = PremiumColors.TerrainDesert.copy(alpha = 0.4f),
        topLeft = Offset(450f, 200f),
        size = Size(150f, 80f)
    )
}

private fun DrawScope.drawTravelRoutes(
    mapCoords: Map<String, Offset>,
    progressMap: Map<String, com.mahmodhota.worldfood3dadventure.game.progress.CountryProgress>
) {
    fun drawRoute(startId: String, endId: String) {
        val start = mapCoords[startId] ?: return
        val end = mapCoords[endId] ?: return
        val isUnlocked = progressMap[endId]?.isUnlocked == true
        val color = if (isUnlocked) PremiumColors.Gold else Color.White.copy(alpha = 0.2f)
        
        drawLine(
            color = color,
            start = start,
            end = end,
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }
    
    drawRoute("germany", "italy")
    drawRoute("italy", "france")
    drawRoute("italy", "sudan")
    drawRoute("germany", "japan")
    drawRoute("france", "mexico")
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
            .offset { IntOffset((x - 12.dp.toPx()).roundToInt(), (y - 12.dp.toPx()).roundToInt()) }
            .size(24.dp)
            .graphicsLayer { rotationZ = angle + 90f },
        contentAlignment = Alignment.Center
    ) {
        Text("✈️", fontSize = 16.sp)
    }
}

@Composable
private fun AtmosphereLayer() {
    // Vignette and subtle clouds
    Box(modifier = Modifier.fillMaxSize().clipToBounds()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f)),
                    radius = 1600f
                )
            )
        )
        
        val infiniteTransition = rememberInfiniteTransition(label = "clouds")
        val cloudOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2000f,
            animationSpec = infiniteRepeatable(
                animation = tween(180000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "cloudOffset"
        )

        repeat(4) { i ->
            Box(
                modifier = Modifier
                    .size(300.dp, 100.dp)
                    .offset {
                        val currentX = ((cloudOffset + i * 500) % 2000) - 400
                        IntOffset(currentX.dp.toPx().roundToInt(), (100 + i * 120).dp.toPx().roundToInt())
                    }
                    .alpha(0.04f)
                    .background(Color.White, RoundedCornerShape(100.dp))
            )
        }
    }
}
