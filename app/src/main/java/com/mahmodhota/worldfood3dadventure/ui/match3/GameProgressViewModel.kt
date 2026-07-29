package com.mahmodhota.worldfood3dadventure.ui.match3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmodhota.worldfood3dadventure.data.progress.GameProgressManager
import com.mahmodhota.worldfood3dadventure.data.progress.model.PersistedGameState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Exposes persistent game state to the UI.
 */
class GameProgressViewModel : ViewModel() {
    private val repository = GameProgressManager.repository
    
    val gameState: StateFlow<PersistedGameState> = repository.state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PersistedGameState()
        )
}
