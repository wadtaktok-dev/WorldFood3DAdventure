package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Premium panel for remaining moves.
 */
@Composable
fun PremiumMovesPanel(
    moves: Int,
    modifier: Modifier = Modifier
) {
    val isUrgent = moves < 5
    val borderColor by animateColorAsState(
        targetValue = if (isUrgent) Color(0xFFFF5252) else PremiumColors.Gold.copy(alpha = 0.5f),
        label = "moveBorder"
    )
    
    Column(
        modifier = modifier
            .width(80.dp)
            .premiumPanel()
            .border(2.dp, borderColor, PremiumShapes.PanelShape)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "MOVES",
            color = Color.LightGray,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = moves.toString(),
            color = if (isUrgent) Color(0xFFFF5252) else Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
