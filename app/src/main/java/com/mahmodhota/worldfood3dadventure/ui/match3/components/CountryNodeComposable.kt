package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.BuildConfig
import com.mahmodhota.worldfood3dadventure.game.progress.CountryProgress
import com.mahmodhota.worldfood3dadventure.game.world.model.CountryMetadata
import com.mahmodhota.worldfood3dadventure.ui.match3.effects.SparkleEffect

/**
 * A glowing marker on the world map representing a country.
 */
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun CountryNodeComposable(
    country: CountryMetadata,
    progress: CountryProgress,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    labelOffset: androidx.compose.ui.geometry.Offset = androidx.compose.ui.geometry.Offset.Zero,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "pressScale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .scale(pressScale)
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
                onLongClick = if (BuildConfig.DEBUG) onLongClick else null
            ),
        contentAlignment = Alignment.Center
    ) {
        // Sparkles for completed countries
        if (progress.isCompleted) {
            SparkleEffect(modifier = Modifier.size(70.dp), count = 5)
        }

        // Glow effect for active/selected
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        Brush.radialGradient(listOf(PremiumColors.Gold.copy(alpha = pulseAlpha), Color.Transparent)),
                        CircleShape
                    )
            )
        }

        // Node Circle
        Surface(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) 3.dp else 1.5.dp,
                    color = if (isSelected) PremiumColors.Gold else if (progress.isUnlocked) Color.White else Color.Gray.copy(alpha = 0.5f),
                    shape = CircleShape
                ),
            color = if (progress.isUnlocked) PremiumColors.DarkSlate else Color.Black.copy(alpha = 0.6f),
            tonalElevation = 6.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (!progress.isUnlocked) {
                    Text(text = "🔒", fontSize = 18.sp)
                } else {
                    Text(text = country.flagEmoji, fontSize = 22.sp)
                }
            }
        }

        // Progress/Stars Overlay
        if (progress.isUnlocked && !progress.isCompleted) {
            val stars = progress.totalStars
            if (stars > 0) {
                Surface(
                    modifier = Modifier
                        .offset(y = 22.dp)
                        .shadow(4.dp, CircleShape),
                    color = PremiumColors.Gold,
                    shape = CircleShape
                ) {
                    Text(
                        text = "⭐ $stars",
                        color = PremiumColors.DeepNavy,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }
        }
        
        if (progress.isCompleted) {
             Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Completed",
                tint = Color(0xFF4CAF50),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(20.dp)
                    .background(Color.White, CircleShape)
            )
        }

        // Label with background for readability
        Surface(
            color = Color.Black.copy(alpha = 0.7f),
            shape = RoundedCornerShape(8.dp),
            border = if (isSelected) BorderStroke(1.dp, PremiumColors.Gold.copy(alpha = 0.5f)) else null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 35.dp) // Base gap
                .offset(x = (labelOffset.x * 0.4f).dp, y = (labelOffset.y * 0.4f).dp) // Scaled offset
        ) {
            Text(
                text = country.displayName.uppercase(),
                color = if (isSelected) PremiumColors.Gold else Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}
