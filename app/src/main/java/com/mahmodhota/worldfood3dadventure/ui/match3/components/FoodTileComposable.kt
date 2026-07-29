package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTile
import com.mahmodhota.worldfood3dadventure.game.match3.model.SpecialTileType

/**
 * Renders a single food tile with a premium glass effect and animated food icon.
 */
@Composable
fun FoodTileComposable(
    tile: FoodTile,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isMatched: Boolean = false
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else if (isMatched) 0.0f else 1.0f,
        animationSpec = tween(if (isMatched) 300 else 300, easing = FastOutSlowInEasing),
        label = "tileScale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (isMatched) 0f else 1f,
        animationSpec = tween(if (isMatched) 250 else 300),
        label = "tileAlpha"
    )

    Box(
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .scale(scale)
            .alpha(alpha)
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PremiumColors.DarkSlate.copy(alpha = 0.85f),
                        PremiumColors.DeepNavy.copy(alpha = 0.95f)
                    )
                )
            )
            .border(
                width = if (isSelected) 2.5.dp else 1.dp,
                color = if (isSelected) PremiumColors.Gold else Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(14.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Subtle Inner Highlight
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
                .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
        )

        // Selection Glow
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            listOf(PremiumColors.Gold.copy(alpha = 0.3f), Color.Transparent)
                        )
                    )
            )
        }

        // The Food Icon
        FoodIcon(
            type = tile.type, 
            size = 36.dp,
            modifier = Modifier.padding(2.dp)
        )

        // Special Tile Overlays with Premium Style
        when (tile.specialType) {
            SpecialTileType.ROW_CLEAR -> SpecialOverlay(icon = "↔️")
            SpecialTileType.COLUMN_CLEAR -> SpecialOverlay(icon = "↕️")
            SpecialTileType.BOMB -> SpecialOverlay(icon = "💣")
            SpecialTileType.COLOR_BOMB -> SpecialOverlay(icon = "🌈")
            else -> {}
        }
    }
}

@Composable
private fun SpecialOverlay(icon: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.1f)),
        contentAlignment = Alignment.BottomEnd
    ) {
        Surface(
            color = PremiumColors.Gold.copy(alpha = 0.9f),
            shape = RoundedCornerShape(topStart = 8.dp),
            modifier = Modifier.size(18.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = icon, fontSize = 10.sp)
            }
        }
    }
}
