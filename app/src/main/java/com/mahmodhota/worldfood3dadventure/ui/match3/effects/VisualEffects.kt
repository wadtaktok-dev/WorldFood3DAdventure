package com.mahmodhota.worldfood3dadventure.ui.match3.effects

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

/**
 * A simple particle system for sparkles and bursts.
 */
@Composable
fun SparkleEffect(
    modifier: Modifier = Modifier,
    color: Color = Color.Yellow,
    count: Int = 10
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sparkle")
    
    val particles = remember {
        List(count) {
            ParticleData(
                initialOffset = Offset(Random.nextFloat(), Random.nextFloat()),
                speed = Random.nextFloat() * 0.05f + 0.02f,
                angle = Random.nextFloat() * 360f
            )
        }
    }

    val animValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleAnim"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            val distance = animValue * 50.dp.toPx()
            val x = (p.initialOffset.x * size.width + Math.cos(Math.toRadians(p.angle.toDouble())).toFloat() * distance)
            val y = (p.initialOffset.y * size.height + Math.sin(Math.toRadians(p.angle.toDouble())).toFloat() * distance)
            
            drawCircle(
                color = color.copy(alpha = 1f - animValue),
                radius = 2.dp.toPx() * (1f - animValue),
                center = Offset(x, y)
            )
        }
    }
}

private data class ParticleData(
    val initialOffset: Offset,
    val speed: Float,
    val angle: Float
)
