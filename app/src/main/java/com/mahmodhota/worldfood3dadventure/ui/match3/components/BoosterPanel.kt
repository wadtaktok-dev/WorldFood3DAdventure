package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Premium Booster Panel for the game screen.
 */
@Composable
fun PremiumBoosterPanel(
    modifier: Modifier = Modifier,
    isHorizontal: Boolean = false
) {
    if (isHorizontal) {
        Row(
            modifier = modifier
                .premiumPanel()
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoosterSlot(icon = "🔨", count = 3)
            BoosterSlot(icon = "🚀", count = 1)
            BoosterSlot(icon = "🖐️", count = 5)
        }
    } else {
        Column(
            modifier = modifier
                .premiumPanel()
                .padding(vertical = 12.dp)
                .width(70.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoosterSlot(icon = "🔨", count = 3)
            BoosterSlot(icon = "🚀", count = 1)
            BoosterSlot(icon = "🖐️", count = 5)
        }
    }
}

@Composable
private fun BoosterSlot(
    icon: String,
    count: Int,
    isSelected: Boolean = false,
    isLocked: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(if (isSelected) PremiumColors.Gold.copy(alpha = 0.3f) else PremiumColors.DarkSlate)
                .border(
                    width = 2.dp,
                    color = if (isSelected) PremiumColors.Gold else PremiumColors.WhiteLow,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = if (isLocked) "🔒" else icon, fontSize = 24.sp)
            
            if (!isLocked) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(18.dp),
                    shape = CircleShape,
                    color = PremiumColors.Gold,
                    tonalElevation = 4.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = count.toString(),
                            color = PremiumColors.DeepNavy,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}
