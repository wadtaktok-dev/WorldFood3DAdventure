package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mahmodhota.worldfood3dadventure.ui.match3.components.*

@Composable
fun ProfileScreenV2(
    onTabSelected: (String) -> Unit,
    progressViewModel: GameProgressViewModel = viewModel()
) {
    val gameState by progressViewModel.gameState.collectAsState()
    val player = gameState.player
    val stats = gameState.stats

    Scaffold(
        topBar = { TopStatusBar(onSettingsClick = {}) },
        bottomBar = { BottomNavigationBar(currentTab = "profile", onTabSelected = onTabSelected) },
        containerColor = PremiumColors.DeepNavy
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            // Header: Avatar & Username
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .premiumPanel()
                    .padding(16.dp)
                    .border(1.dp, PremiumColors.Gold.copy(alpha = 0.3f), PremiumShapes.PanelShape)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Brush.radialGradient(listOf(PremiumColors.Gold, PremiumColors.GoldDark))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👨‍🍳", fontSize = 40.sp)
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = player.username.uppercase(), 
                        color = PremiumColors.Gold, 
                        fontSize = 22.sp, 
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Master Chef Level ${player.level}", 
                        color = Color.White.copy(alpha = 0.7f), 
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Stats Grid
            Text(
                text = "PLAYER STATISTICS", 
                color = Color.White, 
                fontSize = 14.sp, 
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(16.dp))
            
            val statItems = listOf(
                StatItemData("Total Stars", player.totalStars.toString(), Icons.Default.Star, PremiumColors.Gold),
                StatItemData("Coins Found", player.coins.toString(), Icons.Default.Star, PremiumColors.Gold),
                StatItemData("Levels Done", stats.totalCompletedLevels.toString(), Icons.Default.EmojiEvents, Color(0xFF81C784)),
                StatItemData("Best Combo", stats.highestCombo.toString(), Icons.Default.EmojiEvents, Color(0xFFE91E63))
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(220.dp)
            ) {
                items(statItems) { item ->
                    StatCard(item)
                }
            }

            Spacer(Modifier.height(32.dp))

            // Achievements Placeholder
            Text(
                text = "WORLD ACHIEVEMENTS", 
                color = Color.White, 
                fontSize = 14.sp, 
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .premiumPanel()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌟", fontSize = 32.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "ACHIEVEMENT SYSTEM COMING SOON", 
                        color = Color.Gray, 
                        fontSize = 11.sp, 
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

private data class StatItemData(val label: String, val value: String, val icon: ImageVector, val color: Color)

@Composable
private fun StatCard(data: StatItemData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .premiumPanel()
            .border(0.5.dp, PremiumColors.WhiteLow, PremiumShapes.PanelShape)
            .padding(16.dp)
    ) {
        Column {
            Icon(data.icon, contentDescription = null, tint = data.color, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(12.dp))
            Text(text = data.value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = data.label.uppercase(), color = Color.Gray, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}
