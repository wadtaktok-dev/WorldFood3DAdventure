package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTileType
import kotlinx.coroutines.delay

@Composable
fun PremiumCompletionDialog(
    isWin: Boolean,
    score: Int,
    stars: Int,
    collected: Map<FoodTileType, Int>,
    onContinue: () -> Unit,
    onReplay: () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .premiumPanel()
                    .border(2.dp, PremiumColors.Gold, PremiumShapes.PanelShape)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isWin) "DELICIOUS VICTORY!" else "KITCHEN CLOSED",
                    color = if (isWin) PremiumColors.Gold else Color(0xFFFF5252),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                // Stars Animation
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { i ->
                        AnimatedStar(active = isWin && i < stars, delayMillis = i * 200)
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Score Counter
                var displayScore by remember { mutableStateOf(0) }
                LaunchedEffect(score) {
                    if (isWin) {
                        val duration = 1000L
                        val startTime = System.currentTimeMillis()
                        while (System.currentTimeMillis() - startTime < duration) {
                            val progress = (System.currentTimeMillis() - startTime).toFloat() / duration
                            displayScore = (score * progress).toInt()
                            delay(16)
                        }
                    }
                    displayScore = score
                }

                Text(
                    text = "SCORE: $displayScore",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black
                )

                Spacer(Modifier.height(24.dp))

                // Collection Summary (Mini)
                if (isWin && collected.isNotEmpty()) {
                    Text(
                        text = "COLLECTED DISHES",
                        color = Color.Gray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        collected.keys.take(5).forEach { type ->
                            FoodIcon(type = type, size = 28.dp)
                        }
                    }
                }

                Spacer(Modifier.height(40.dp))

                // Buttons
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = onContinue,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isWin) Color(0xFF4CAF50) else PremiumColors.DarkSlate)
                    ) {
                        Text(
                            text = if (isWin) "CONTINUE ADVENTURE" else "BACK TO HUB",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                    }
                    
                    if (!isWin) {
                        OutlinedButton(
                            onClick = onReplay,
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PremiumColors.Gold)
                        ) {
                            Text("TRY AGAIN", color = PremiumColors.Gold, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedStar(active: Boolean, delayMillis: Int) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(active) {
        if (active) {
            delay(delayMillis.toLong())
            visible = true
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1.2f else 0.8f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "starScale"
    )

    Text(
        text = if (active && visible) "⭐" else "☆",
        fontSize = 48.sp,
        color = if (active && visible) PremiumColors.Gold else Color.Gray.copy(alpha = 0.3f),
        modifier = Modifier.scale(scale)
    )
}
