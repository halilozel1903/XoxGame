package com.halil.ozel.xoxgame.domain.usecase

import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player
import com.halil.ozel.xoxgame.domain.repository.GameRepository

class MakeMoveUseCase(private val repository: GameRepository) {
    operator fun invoke(row: Int, col: Int, player: Player): Board =
        repository.makeMove(row, col, player)
}
