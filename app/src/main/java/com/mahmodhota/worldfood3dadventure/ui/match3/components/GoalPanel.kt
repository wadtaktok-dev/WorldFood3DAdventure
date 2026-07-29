package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.match3.model.FoodTileType
import com.mahmodhota.worldfood3dadventure.game.match3.model.LevelGoal

/**
 * Premium panel displaying level objectives and progress.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PremiumGoalPanel(
    goals: List<LevelGoal>,
    collected: Map<FoodTileType, Int>,
    currentScore: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .widthIn(min = 100.dp)
            .premiumPanel()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MISSION",
            color = PremiumColors.Gold,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        
        Spacer(Modifier.height(8.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.heightIn(max = 200.dp)
        ) {
            items(goals) { goal ->
                when (goal) {
                    is LevelGoal.CollectFood -> {
                        val count = collected[goal.type] ?: 0
                        val isDone = count >= goal.amount
                        GoalItem(
                            icon = { FoodIcon(type = goal.type, size = 24.dp) },
                            progress = "$count/${goal.amount}",
                            isCompleted = isDone
                        )
                    }
                    is LevelGoal.ScoreTarget -> {
                        val isDone = currentScore >= goal.target
                        GoalItem(
                            icon = { Text("⭐", fontSize = 18.sp) },
                            progress = "$currentScore/${goal.target}",
                            isCompleted = isDone
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GoalItem(
    icon: @Composable () -> Unit,
    progress: String,
    isCompleted: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            icon()
        }
        
        Spacer(Modifier.width(8.dp))
        
        Text(
            text = progress,
            color = if (isCompleted) Color(0xFF81C784) else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
        
        if (isCompleted) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = "Completed",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(16.dp).padding(start = 4.dp)
            )
        }
    }
}
