package com.halil.ozel.xoxgame.presentation.state

import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player

data class GameUiState(
    val board: Board,
    val currentPlayer: Player,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val isResetEnabled: Boolean = false
)