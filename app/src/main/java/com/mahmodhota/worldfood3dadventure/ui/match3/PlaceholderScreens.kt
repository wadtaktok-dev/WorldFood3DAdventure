package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.ui.match3.components.BottomNavigationBar
import com.mahmodhota.worldfood3dadventure.ui.match3.components.TopStatusBar

@Composable
fun RewardsScreen(onTabSelected: (String) -> Unit) {
    PlaceholderScreen(title = "Rewards", tab = "rewards", onTabSelected = onTabSelected)
}

@Composable
private fun PlaceholderScreen(title: String, tab: String, onTabSelected: (String) -> Unit) {
    Scaffold(
        topBar = { TopStatusBar(onSettingsClick = {}) },
        bottomBar = { BottomNavigationBar(currentTab = tab, onTabSelected = onTabSelected) },
        containerColor = Color(0xFF121212)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = title, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(8.dp))
                Text(text = "Coming Soon...", color = Color.Gray, fontSize = 16.sp)
            }
        }
    }
}
