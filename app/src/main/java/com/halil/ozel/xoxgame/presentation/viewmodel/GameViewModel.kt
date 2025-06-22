package com.halil.ozel.xoxgame.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.halil.ozel.xoxgame.di.AppModule
import com.halil.ozel.xoxgame.domain.model.Player

/**
 * Created by halilozel1903 on 22.06.2025.
 */

class GameViewModel : ViewModel() {

    var board by mutableStateOf(AppModule.getBoardUseCase())
        private set

    var currentPlayer by mutableStateOf(Player.X)
        private set

    var winner by mutableStateOf<Player?>(null)
        private set

    var isDraw by mutableStateOf(false)
        private set

    fun onCellClicked(row: Int, col: Int) {
        if (board.cells[row][col] != null || winner != null || isDraw) return
        board = AppModule.makeMoveUseCase(row, col, currentPlayer)
        checkGameStatus()
        if (winner == null && !isDraw) {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
    }

    private fun checkGameStatus() {
        // Rows & Columns
        for (i in 0..2) {
            if (board.cells[i][0] != null && board.cells[i][0] == board.cells[i][1] && board.cells[i][1] == board.cells[i][2])
                winner = board.cells[i][0]
            if (board.cells[0][i] != null && board.cells[0][i] == board.cells[1][i] && board.cells[1][i] == board.cells[2][i])
                winner = board.cells[0][i]
        }
        // Diagonals
        if (board.cells[0][0] != null && board.cells[0][0] == board.cells[1][1] && board.cells[1][1] == board.cells[2][2])
            winner = board.cells[0][0]
        if (board.cells[0][2] != null && board.cells[0][2] == board.cells[1][1] && board.cells[1][1] == board.cells[2][0])
            winner = board.cells[0][2]
        // Draw
        if (winner == null && board.cells.all { row -> row.all { it != null } }) {
            isDraw = true
        }
    }

    fun resetGame() {
        board = AppModule.resetGameUseCase()
        winner = null
        isDraw = false
        currentPlayer = Player.X
    }
}