package com.halil.ozel.xoxgame.domain.usecase

import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.repository.GameRepository

class GetBoardUseCase(private val repository: GameRepository) {
    operator fun invoke(): Board = repository.getBoard()
}
