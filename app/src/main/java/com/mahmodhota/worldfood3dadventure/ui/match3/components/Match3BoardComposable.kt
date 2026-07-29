package com.mahmodhota.worldfood3dadventure.ui.match3.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahmodhota.worldfood3dadventure.game.match3.model.BoardPosition
import com.mahmodhota.worldfood3dadventure.game.match3.model.Match3Board
import kotlinx.coroutines.delay

/**
 * Renders the interactive Match-3 board with animated tiles.
 */
@Composable
fun Match3BoardComposable(
    board: Match3Board,
    selectedPosition: BoardPosition?,
    matchedPositions: Set<BoardPosition>,
    onTileClick: (BoardPosition) -> Unit,
    modifier: Modifier = Modifier,
    comboCount: Int = 0
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Calculate the maximum possible board size while keeping it square and respecting padding
        val boardPadding = 8.dp
        val maxAvailableWidth = maxWidth - (boardPadding * 2)
        val maxAvailableHeight = maxHeight - (boardPadding * 2)
        val boardSize = minOf(maxAvailableWidth, maxAvailableHeight).coerceAtLeast(0.dp)
        
        if (boardSize > 0.dp) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(boardSize)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF222222))
                        .padding(4.dp) // Internal spacing between border and tiles
                        .clipToBounds()
                        .pointerInput(board, boardSize) {
                            val boardSizePx = boardSize.toPx()
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val col = (offset.x / (boardSizePx / board.columns)).toInt()
                                    val row = (offset.y / (boardSizePx / board.rows)).toInt()
                                    if (row in 0 until board.rows && col in 0 until board.columns) {
                                        onTileClick(BoardPosition(row, col))
                                    }
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    val threshold = boardSizePx / board.columns / 2
                                    if (selectedPosition != null) {
                                        val targetPos = when {
                                            dragAmount.x > threshold -> selectedPosition.copy(column = selectedPosition.column + 1)
                                            dragAmount.x < -threshold -> selectedPosition.copy(column = selectedPosition.column - 1)
                                            dragAmount.y > threshold -> selectedPosition.copy(row = selectedPosition.row + 1)
                                            dragAmount.y < -threshold -> selectedPosition.copy(row = selectedPosition.row - 1)
                                            else -> null
                                        }
                                        if (targetPos != null && board.contains(targetPos)) {
                                            onTileClick(targetPos)
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    // Adjust tile size to fit perfectly inside the padded box
                    val innerBoardSize = boardSize - 8.dp // Account for the 4.dp padding on all sides
                    val tileSize = innerBoardSize / board.columns
                    val density = LocalDensity.current
                    val tileSizePx = with(density) { tileSize.toPx() }

                    for (r in 0 until board.rows) {
                        for (c in 0 until board.columns) {
                            val pos = BoardPosition(r, c)
                            val tile = board.tileAt(pos)
                            
                            if (tile != null) {
                                val isSelected = selectedPosition == pos
                                val isMatched = matchedPositions.contains(pos)
                                
                                // Use offset instead of graphicsLayer for more predictable layout
                                val animX = tileSize * c
                                
                                // Still use Animatable for Y to handle the "drop-in" effect
                                val animY = remember(tile.id) { Animatable(-tileSizePx) }
                                LaunchedEffect(tile.id, r, tileSizePx) {
                                    // Update animY if tile ID or row or board size changes
                                    if (tileSizePx > 0) {
                                        animY.animateTo(
                                            targetValue = r * tileSizePx,
                                            animationSpec = tween(durationMillis = 400)
                                        )
                                    }
                                }

                                key(tile.id) {
                                    Box(
                                        modifier = Modifier
                                            .size(tileSize)
                                            .offset {
                                                IntOffset(
                                                    x = with(density) { animX.roundToPx() },
                                                    y = animY.value.toInt()
                                                )
                                            }
                                            .clickable { onTileClick(pos) }
                                    ) {
                                        FoodTileComposable(
                                            tile = tile,
                                            isSelected = isSelected,
                                            isMatched = isMatched
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Combo Overlay
                if (comboCount > 1) {
                    ComboOverlay(count = comboCount)
                }
            }
        }
    }
}

@Composable
private fun ComboOverlay(count: Int) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(count) {
        visible = true
        delay(1000L)
        visible = false
    }
    
    androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.scaleIn(initialScale = 0.5f),
        exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.scaleOut(targetScale = 1.5f)
    ) {
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .border(2.dp, PremiumColors.Gold, RoundedCornerShape(24.dp))
        ) {
            Text(
                text = "COMBO x$count!",
                color = PremiumColors.Gold,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
        }
    }
}
