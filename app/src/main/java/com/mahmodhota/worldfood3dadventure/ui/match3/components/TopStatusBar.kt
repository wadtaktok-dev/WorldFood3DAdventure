package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.progress.PlayerProfile

/**
 * Modern top status bar for the game hub.
 */
@Composable
fun TopStatusBar(onSettingsClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = PremiumColors.DeepNavy,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Stats Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                StatCapsule(icon = "❤️", value = PlayerProfile.lives.toString(), color = Color(0xFFFF5252))
                StatCapsule(icon = "🪙", value = PlayerProfile.coins.toString(), color = PremiumColors.Gold)
                StatCapsule(icon = "⭐", value = PlayerProfile.stars.toString(), color = Color(0xFF448AFF))
            }

            Spacer(Modifier.width(8.dp))

            // Right: Player Level and Settings
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 8.dp)) {
                    Text(
                        text = "LVL ${PlayerProfile.level}",
                        color = PremiumColors.Gold,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                    XpProgressBar(progress = PlayerProfile.xpProgress)
                }
                
                IconButton(
                    onClick = onSettingsClick, 
                    modifier = Modifier
                        .size(36.dp)
                        .background(PremiumColors.WhiteLow.copy(alpha = 0.1f), CircleShape)
                        .border(1.dp, PremiumColors.WhiteLow, CircleShape)
                ) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCapsule(icon: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .clip(PremiumShapes.CapsuleShape)
            .background(PremiumColors.DarkSlate)
            .border(1.dp, color.copy(alpha = 0.4f), PremiumShapes.CapsuleShape)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = icon, fontSize = 12.sp, modifier = Modifier.semantics { contentDescription = "Icon" })
        Text(
            text = value, 
            color = Color.White, 
            fontWeight = FontWeight.Bold, 
            fontSize = 12.sp,
            maxLines = 1,
            modifier = Modifier.semantics { contentDescription = value }
        )
    }
}

@Composable
private fun XpProgressBar(progress: Float) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "xpProgress")
    
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(6.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .border(0.5.dp, Color.White.copy(alpha = 0.2f), CircleShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(CircleShape)
                .background(Brush.horizontalGradient(listOf(Color(0xFF81C784), Color(0xFF4CAF50))))
        )
    }
}
