package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.match3.Match3LevelRegistry
import com.mahmodhota.worldfood3dadventure.game.match3.model.LevelGoal
import com.mahmodhota.worldfood3dadventure.ui.match3.components.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import com.mahmodhota.worldfood3dadventure.game.progress.PlayerProfile
import com.mahmodhota.worldfood3dadventure.game.progress.ProgressionManager
import com.mahmodhota.worldfood3dadventure.game.world.LevelRegistry
import kotlinx.coroutines.launch

/**
 * Premium Combined Adventure Screen (HUB).
 * Integrates World Map and Match-3 gameplay into a single hierarchical view.
 */
@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun PremiumAdventureScreen(
    countryId: String,
    levelNumber: Int,
    onTabSelected: (String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val viewModel: Match3ViewModel = remember(countryId, levelNumber) {
        Match3ViewModel(countryId, levelNumber)
    }
    val match3State = viewModel.uiState
    
    val levelDef = remember(countryId, levelNumber) {
        Match3LevelRegistry.getLevel(countryId, levelNumber)
    }

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val mapRequester = remember { BringIntoViewRequester() }
    val boardRequester = remember { BringIntoViewRequester() }
    
    var focusedSection by remember { mutableStateOf("puzzle") }

    // Map State
    var mapScale by remember { mutableStateOf(1.0f) }
    var mapOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
        mapScale = (mapScale * zoomChange).coerceIn(0.5f, 4f)
        mapOffset += offsetChange
    }

    Scaffold(
        topBar = { TopStatusBar(onSettingsClick = onSettingsClick) },
        containerColor = PremiumColors.DeepNavy
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. World Map Section
            AdventureMapSection(
                mapScale = mapScale,
                mapOffset = mapOffset,
                transformState = transformState,
                selectedCountryId = countryId,
                modifier = Modifier.bringIntoViewRequester(mapRequester)
            )

            // 2. Current Level Header
            CurrentLevelHeader(
                countryName = countryId.replaceFirstChar { it.uppercase() },
                levelNumber = levelNumber,
                levelTitle = levelDef?.title
            )

            // 3. Match-3 Section (Goal + Board + Booster)
            CombinedMatch3Section(
                viewModel = viewModel,
                state = match3State,
                modifier = Modifier.bringIntoViewRequester(boardRequester)
            )

            // 4. Bottom Cards
            AdventureBottomCards(
                currentCountryId = countryId,
                currentLevel = levelNumber,
                focusedSection = focusedSection,
                onCardClick = { section ->
                    focusedSection = section
                    when (section) {
                        "travel" -> {
                            coroutineScope.launch {
                                mapRequester.bringIntoView()
                            }
                        }
                        "puzzle" -> {
                            coroutineScope.launch {
                                boardRequester.bringIntoView()
                            }
                        }
                        "rewards" -> onTabSelected("rewards")
                    }
                }
            )
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AdventureMapSection(
    mapScale: Float,
    mapOffset: Offset,
    transformState: androidx.compose.foundation.gestures.TransformableState,
    selectedCountryId: String,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(PremiumColors.DeepNavy)
            .border(1.dp, PremiumColors.WhiteLow, RoundedCornerShape(24.dp))
    ) {
        // Calculate responsive height based on 1000:500 (2:1) aspect ratio
        val responsiveHeight = (maxWidth.value * (WorldMapGeometry.MAP_HEIGHT / WorldMapGeometry.MAP_WIDTH)).dp
        val dynamicHeight = responsiveHeight.coerceIn(200.dp, 400.dp)
        
        Box(modifier = Modifier.height(dynamicHeight)) {
            WorldMapComponent(
                mapScale = mapScale,
                offset = mapOffset,
                state = transformState,
                selectedCountryId = selectedCountryId,
                onCountryClick = { /* Handled via parent selection */ },
                onCountryLongClick = { /* Handled via parent selection */ }
            )
        }
    }
}

@Composable
private fun CurrentLevelHeader(
    countryName: String,
    levelNumber: Int,
    levelTitle: String? = null
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(countryName, levelNumber) {
        visible = false
        kotlinx.coroutines.delay(kotlin.time.Duration.parse("100ms"))
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically() + slideInVertically { -20 },
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            color = PremiumColors.DarkSlate,
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, PremiumColors.GoldGradient)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${countryName.uppercase()} - LEVEL $levelNumber",
                        color = PremiumColors.Gold,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    )
                }
                
                levelTitle?.let { title ->
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun CombinedMatch3Section(
    viewModel: Match3ViewModel,
    state: Match3UiState,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val isWide = maxWidth > 600.dp
        
        if (isWide) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PremiumGoalPanel(
                        goals = state.goals,
                        collected = state.collectedCounts,
                        currentScore = state.score
                    )
                    PremiumMovesPanel(moves = state.movesRemaining)
                }

                Match3BoardComposable(
                    board = state.board,
                    selectedPosition = state.selectedPosition,
                    matchedPositions = state.matchedPositions,
                    onTileClick = { viewModel.onTileSelected(it) },
                    modifier = Modifier.weight(1f),
                    comboCount = state.comboCount
                )

                PremiumBoosterPanel(isHorizontal = false)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PremiumGoalPanel(
                        goals = state.goals,
                        collected = state.collectedCounts,
                        currentScore = state.score,
                        modifier = Modifier.weight(1f)
                    )
                    PremiumMovesPanel(moves = state.movesRemaining)
                }

                Match3BoardComposable(
                    board = state.board,
                    selectedPosition = state.selectedPosition,
                    matchedPositions = state.matchedPositions,
                    onTileClick = { viewModel.onTileSelected(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                PremiumBoosterPanel(isHorizontal = true)
            }
        }
    }
}

@Composable
private fun AdventureBottomCards(
    currentCountryId: String,
    currentLevel: Int,
    focusedSection: String,
    onCardClick: (String) -> Unit
) {
    val unlockedCount = ProgressionManager.progressMap.values.count { it.isUnlocked }
    val totalCountries = LevelRegistry.allCountryIds.size
    val travelProgress = unlockedCount.toFloat() / totalCountries.toFloat()
    
    val countryProgress = ProgressionManager.getCountryProgress(currentCountryId)
    val earnedStars = countryProgress.totalStars
    val totalStarsPossible = countryProgress.levels.size * 3
    val puzzleProgress = if (totalStarsPossible > 0) earnedStars.toFloat() / totalStarsPossible.toFloat() else 0f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .heightIn(max = 140.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PremiumAdventureCard(
            title = "TRAVEL",
            subtitle = currentCountryId.replaceFirstChar { it.uppercase() },
            icon = "🌍",
            isSelected = focusedSection == "travel",
            progress = travelProgress,
            onClick = { onCardClick("travel") },
            modifier = Modifier.width(120.dp)
        )
        
        PremiumAdventureCard(
            title = "PUZZLE",
            subtitle = "Level $currentLevel",
            icon = "🧩",
            isSelected = focusedSection == "puzzle",
            progress = puzzleProgress,
            onClick = { onCardClick("puzzle") },
            modifier = Modifier.width(120.dp)
        )
        
        PremiumAdventureCard(
            title = "REWARDS",
            subtitle = "${PlayerProfile.stars} Stars",
            icon = "🎁",
            isSelected = focusedSection == "rewards",
            onClick = { onCardClick("rewards") },
            modifier = Modifier.width(120.dp)
        )
    }
}

// Old placeholders removed as they are now in separate files or replaced by premium panels

