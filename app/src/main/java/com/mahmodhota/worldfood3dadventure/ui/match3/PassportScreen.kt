package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import com.mahmodhota.worldfood3dadventure.ui.match3.components.*

@Composable
fun PassportScreen(
    onTabSelected: (String) -> Unit,
    progressViewModel: GameProgressViewModel = viewModel()
) {
    val gameState by progressViewModel.gameState.collectAsState()
    val countries = gameState.countries

    Scaffold(
        topBar = { TopStatusBar(onSettingsClick = {}) },
        bottomBar = { BottomNavigationBar(currentTab = "book", onTabSelected = onTabSelected) },
        containerColor = PremiumColors.DeepNavy
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WORLD PASSPORT", 
                color = PremiumColors.Gold, 
                fontSize = 24.sp, 
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(LevelRegistry.allCountries) { country ->
                    val progress = countries[country.levelId]
                    StampNode(
                        flag = country.flagEmoji,
                        name = country.displayName,
                        isCleared = progress?.isCompleted ?: false
                    )
                }
            }
        }
    }
}

@Composable
private fun StampNode(flag: String, name: String, isCleared: Boolean) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .premiumPanel()
            .border(if (isCleared) 2.dp else 1.dp, if (isCleared) PremiumColors.Gold else PremiumColors.WhiteLow, PremiumShapes.PanelShape),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (isCleared) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.3f))
                    .border(1.dp, if (isCleared) PremiumColors.Gold.copy(alpha = 0.5f) else Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = flag, fontSize = 32.sp, modifier = Modifier.alpha(if (isCleared) 1f else 0.2f))
            }
            
            Spacer(Modifier.height(8.dp))
            
            Text(
                text = name.uppercase(), 
                color = if (isCleared) Color.White else Color.Gray, 
                fontSize = 12.sp, 
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            
            if (isCleared) {
                Text(
                    text = "EXPLORED",
                    color = PremiumColors.Gold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "LOCKED",
                    color = Color.Gray.copy(alpha = 0.5f),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
