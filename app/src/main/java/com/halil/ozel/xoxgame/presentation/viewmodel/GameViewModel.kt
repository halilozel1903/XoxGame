package com.halil.ozel.xoxgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player
import com.halil.ozel.xoxgame.domain.usecase.GetBoardUseCase
import com.halil.ozel.xoxgame.domain.usecase.MakeMoveUseCase
import com.halil.ozel.xoxgame.domain.usecase.ResetGameUseCase
import com.halil.ozel.xoxgame.presentation.state.GameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(
    private val getBoardUseCase: GetBoardUseCase,
    private val makeMoveUseCase: MakeMoveUseCase,
    private val resetGameUseCase: ResetGameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(createState(getBoardUseCase(), Player.X))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun onCellClicked(row: Int, col: Int) {
        val current = _uiState.value
        if (current.board.cell(row, col) != null || current.winner != null || current.isDraw) return

        val updatedBoard = makeMoveUseCase(row, col, current.currentPlayer)
        val nextPlayer = if (updatedBoard.winner == null && !updatedBoard.isDraw) {
            current.currentPlayer.opponent
        } else {
            current.currentPlayer
        }
        _uiState.value = createState(updatedBoard, nextPlayer)
    }

    fun resetGame() {
        _uiState.value = createState(resetGameUseCase(), Player.X)
    }

    private fun createState(board: Board, currentPlayer: Player) = GameUiState(
        board = board,
        currentPlayer = currentPlayer,
        winner = board.winner,
        isDraw = board.isDraw,
        isResetEnabled = board.hasMarks
    )
}
