package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object PremiumColors {
    val DeepNavy = Color(0xFF0D1B2A)
    val DarkSlate = Color(0xFF1B263B)
    val MutedBlue = Color(0xFF415A77)
    val Gold = Color(0xFFFFD700)
    val GoldLight = Color(0xFFFFEC8B)
    val GoldDark = Color(0xFFB8860B)
    val WhiteHigh = Color.White
    val WhiteMed = Color.White.copy(alpha = 0.7f)
    val WhiteLow = Color.White.copy(alpha = 0.4f)
    
    val GoldGradient = Brush.verticalGradient(
        colors = listOf(GoldLight, Gold, GoldDark)
    )
    
    val PanelGradient = Brush.verticalGradient(
        colors = listOf(DarkSlate, DeepNavy)
    )

    // Adventure Map V2 Colors
    val OceanDeep = Color(0xFF0D1B2A)
    val OceanMid = Color(0xFF1B263B)
    val OceanShallow = Color(0xFF415A77)
    val CoastHighlight = Color(0xFF64FFDA).copy(alpha = 0.3f)
    
    val TerrainForest = Color(0xFF2D6A4F)
    val TerrainMountain = Color(0xFF4A4E69)
    val TerrainDesert = Color(0xFFE9C46A)
    val TerrainTundra = Color(0xFFE0E1DD)
    
    val LandShadow = Color.Black.copy(alpha = 0.25f)

    // Food Icon Drawing Constants
    val IconOutline = Color(0xFF2B2B2B)
    val IconGloss = Color.White.copy(alpha = 0.35f)
    val IconShadow = Color.Black.copy(alpha = 0.25f)
}

object PremiumShapes {
    val PanelShape = RoundedCornerShape(16.dp)
    val CapsuleShape = RoundedCornerShape(50)
}

fun Modifier.premiumPanel() = this
    .shadow(8.dp, PremiumShapes.PanelShape)
    .clip(PremiumShapes.PanelShape)
    .background(PremiumColors.PanelGradient)
    .border(1.dp, PremiumColors.WhiteLow, PremiumShapes.PanelShape)

fun Modifier.goldBorder() = this
    .border(1.5.dp, PremiumColors.GoldGradient, PremiumShapes.PanelShape)
