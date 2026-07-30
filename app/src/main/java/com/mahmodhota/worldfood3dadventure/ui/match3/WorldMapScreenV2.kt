package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.progress.ProgressionManager
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import com.mahmodhota.worldfood3dadventure.ui.match3.components.*
import kotlin.math.atan2

/**
 * High-fidelity interactive World Map screen with organic artwork.
 */
@Composable
fun WorldMapScreenV2(
    onLevelSelected: (String, Int) -> Unit,
    onTabSelected: (String) -> Unit
) {
    var mapScale by rememberSaveable { mutableStateOf(1.0f) }
    var offsetX by rememberSaveable { mutableStateOf(0f) }
    var offsetY by rememberSaveable { mutableStateOf(0f) }
    
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
        mapScale = (mapScale * zoomChange).coerceIn(0.5f, 4f)
        offsetX += offsetChange.x
        offsetY += offsetChange.y
    }

    var selectedCountryId by remember { mutableStateOf<String?>(null) }
    val progressMap = ProgressionManager.progressMap

    Scaffold(
        topBar = { TopStatusBar(onSettingsClick = {}) },
        bottomBar = { BottomNavigationBar(currentTab = "world", onTabSelected = onTabSelected) },
        containerColor = PremiumColors.DeepNavy,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        WorldMapComponent(
            mapScale = mapScale,
            offset = Offset(offsetX, offsetY),
            state = state,
            selectedCountryId = selectedCountryId,
            onCountryClick = { id ->
                selectedCountryId = id
                com.mahmodhota.worldfood3dadventure.data.audio.GlobalSystemManager.audio.playSfx(com.mahmodhota.worldfood3dadventure.data.audio.SfxType.BUTTON_CLICK)
            },
            onCountryLongClick = { id ->
                onLevelSelected(id, 1)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }

    selectedCountryId?.let { id ->
        val country = LevelRegistry.allCountries.find { it.levelId == id } ?: return@let
        val progress = progressMap[id] ?: com.mahmodhota.worldfood3dadventure.game.progress.CountryProgress(id)
        
        CountryInfoBottomSheet(
            country = country,
            progress = progress,
            onLevelSelected = { lvl -> onLevelSelected(id, lvl) },
            onDismiss = { selectedCountryId = null }
        )
    }
}
