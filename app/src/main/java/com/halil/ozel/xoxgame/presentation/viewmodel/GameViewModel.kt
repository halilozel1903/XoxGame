package com.halil.ozel.xoxgame.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.halil.ozel.xoxgame.di.AppModule
import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player
import com.halil.ozel.xoxgame.presentation.state.GameUiState

class GameViewModel : ViewModel() {

    var uiState by mutableStateOf(
        GameUiState(
            board = AppModule.getBoardUseCase(),
            currentPlayer = Player.X
        )
    )
        private set

    fun onCellClicked(row: Int, col: Int) {
        if (uiState.board.cells[row][col] != null || uiState.winner != null || uiState.isDraw) return
        val updatedBoard = AppModule.makeMoveUseCase(row, col, uiState.currentPlayer)
        val winner = calculateWinner(updatedBoard)
        val isDraw = winner == null && updatedBoard.cells.all { row -> row.all { it != null } }
        val nextPlayer = if (winner == null && !isDraw) {
            if (uiState.currentPlayer == Player.X) Player.O else Player.X
        } else {
            uiState.currentPlayer
        }
        val isResetEnabled =
            updatedBoard.cells.any { row -> row.any { it != null } } || winner != null || isDraw

        uiState = uiState.copy(
            board = updatedBoard,
            currentPlayer = nextPlayer,
            winner = winner,
            isDraw = isDraw,
            isResetEnabled = isResetEnabled
        )
    }

    private fun calculateWinner(board: Board): Player? {
        // Rows & Columns
        for (i in 0..2) {
            if (board.cells[i][0] != null && board.cells[i][0] == board.cells[i][1] && board.cells[i][1] == board.cells[i][2])
                return board.cells[i][0]
            if (board.cells[0][i] != null && board.cells[0][i] == board.cells[1][i] && board.cells[1][i] == board.cells[2][i])
                return board.cells[0][i]
        }
        // Diagonals
        if (board.cells[0][0] != null && board.cells[0][0] == board.cells[1][1] && board.cells[1][1] == board.cells[2][2])
            return board.cells[0][0]
        if (board.cells[0][2] != null && board.cells[0][2] == board.cells[1][1] && board.cells[1][1] == board.cells[2][0])
            return board.cells[0][2]
        return null
    }

    fun resetGame() {
        val emptyBoard = AppModule.resetGameUseCase()
        uiState = uiState.copy(
            board = emptyBoard,
            currentPlayer = Player.X,
            winner = null,
            isDraw = false,
            isResetEnabled = false
        )
    }
}