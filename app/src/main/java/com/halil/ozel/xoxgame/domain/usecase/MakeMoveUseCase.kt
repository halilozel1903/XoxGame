package com.halil.ozel.xoxgame.domain.usecase

import com.halil.ozel.xoxgame.domain.repository.GameRepository
import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player

/**
 * Created by halilozel1903 on 22.06.2025.
 */
class MakeMoveUseCase(private val repository: GameRepository) {
    operator fun invoke(row: Int, col: Int, player: Player): Board {
        return repository.makeMove(row, col, player)
    }
}