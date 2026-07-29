package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Renders an animated curved dashed path between two points with a subtle glow.
 */
@Composable
fun TravelPathComposable(
    start: Offset,
    end: Offset,
    isUnlocked: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "path")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pathPhase"
    )

    // Calculate a control point for the curve (midpoint with an offset)
    val mid = Offset((start.x + end.x) / 2, (start.y + end.y) / 2)
    val dx = end.x - start.x
    val dy = end.y - start.y
    // Offset perpendicular to the line to create a curve
    val controlPoint = Offset(mid.x - dy * 0.25f, mid.y + dx * 0.25f)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(start.x, start.y)
            quadraticTo(controlPoint.x, controlPoint.y, end.x, end.y)
        }

        // Draw Glow / Shadow
        drawPath(
            path = path,
            color = Color.Black.copy(alpha = 0.2f),
            style = Stroke(width = 6f)
        )

        // Draw Main Path
        drawPath(
            path = path,
            color = if (isUnlocked) PremiumColors.Gold else Color.White.copy(alpha = 0.3f),
            style = Stroke(
                width = 2.5f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), phase)
            )
        )
        
        // Traveling Pulse (only if unlocked)
        if (isUnlocked) {
             drawPath(
                path = path,
                color = Color.White.copy(alpha = 0.4f),
                style = Stroke(
                    width = 4f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(1f, 40f), phase * 0.5f)
                )
            )
        }
    }
}
