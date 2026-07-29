package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.progress.CountryProgress
import com.mahmodhota.worldfood3dadventure.game.progress.Match3LevelProgress
import com.mahmodhota.worldfood3dadventure.game.world.model.CountryMetadata

/**
 * Modern information panel for a selected country.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryInfoBottomSheet(
    country: CountryMetadata,
    progress: CountryProgress,
    onLevelSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1E1E),
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Gray) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding()
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = country.flagEmoji, fontSize = 48.sp)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = country.displayName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "${progress.completionPercentage}% Explored • ${progress.totalStars} Stars",
                        color = Color(0xFF4CAF50),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = country.travelDescription,
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Select a Level",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            // Level Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 56.dp),
                modifier = Modifier.heightIn(max = 240.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(progress.levels) { level ->
                    LevelNode(level = level, onClick = { if (level.isUnlocked) onLevelSelected(level.levelNumber) })
                }
            }

            Spacer(Modifier.height(32.dp))

            // Play/Travel Button
            Button(
                onClick = { 
                    val nextLevel = progress.levels.firstOrNull { !it.isCompleted && it.isUnlocked }?.levelNumber ?: 1
                    onLevelSelected(nextLevel) 
                },
                enabled = progress.isUnlocked,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "RESUME ADVENTURE", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun LevelNode(level: Match3LevelProgress, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            onClick = onClick,
            enabled = level.isUnlocked,
            modifier = Modifier.size(45.dp),
            shape = CircleShape,
            color = if (level.isCompleted) Color(0xFF4CAF50) else if (level.isUnlocked) Color(0xFF2D2D2D) else Color.DarkGray,
            border = if (level.isUnlocked) BorderStroke(2.dp, Color.White.copy(alpha = 0.5f)) else null
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (level.isUnlocked) {
                    Text(text = level.levelNumber.toString(), color = Color.White, fontWeight = FontWeight.Bold)
                } else {
                    Text(text = "🔒", fontSize = 12.sp)
                }
            }
        }
        Row {
            repeat(3) { i ->
                Text(
                    text = "★",
                    fontSize = 8.sp,
                    color = if (i < level.stars) Color(0xFFFFD740) else Color.Gray
                )
            }
        }
    }
}
