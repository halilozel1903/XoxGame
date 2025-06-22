package com.halil.ozel.xoxgame.data

/**
 * Created by halilozel1903 on 22.06.2025.
 */
import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player
import com.halil.ozel.xoxgame.domain.repository.GameRepository

class GameRepositoryImpl : GameRepository {
    private var board: Board = Board.empty()

    override fun getBoard(): Board = board

    override fun makeMove(row: Int, col: Int, player: Player): Board {
        if (board.cells[row][col] != null) return board
        val newCells = board.cells.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, cell ->
                if (r == row && c == col) player else cell
            }
        }
        board = Board(newCells)
        return board
    }

    override fun resetGame(): Board {
        board = Board.empty()
        return board
    }
}