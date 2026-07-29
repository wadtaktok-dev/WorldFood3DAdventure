package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Authoritative transformation model for the World Map.
 * Decouples logical 1000x650 coordinates from screen pixels and user interaction.
 */
data class MapTransform(
    val baseScale: Float,
    val baseOffsetX: Float,
    val baseOffsetY: Float,
    val userScale: Float,
    val userOffset: Offset
) {
    /**
     * Maps a logical coordinate (0..1000, 0..650) to actual screen pixels.
     */
    fun logicalToScreen(logical: Offset): Offset {
        // 1. Base Fit
        val bx = logical.x * baseScale + baseOffsetX
        val by = logical.y * baseScale + baseOffsetY
        
        // 2. User Pan/Zoom (Assuming TransformOrigin.Center)
        // Note: For markers, we usually apply pan/zoom via a parent graphicsLayer.
        // If we need absolute screen position, we'd need the viewport center.
        return Offset(bx, by)
    }

    /**
     * Total scale applied to logical units.
     */
    val totalScale: Float get() = baseScale * userScale
}
