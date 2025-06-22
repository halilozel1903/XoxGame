package com.halil.ozel.xoxgame.domain.usecase

import com.halil.ozel.xoxgame.domain.repository.GameRepository
import com.halil.ozel.xoxgame.domain.model.Board

/**
 * Created by halilozel1903 on 22.06.2025.
 */
class ResetGameUseCase(private val repository: GameRepository) {
    operator fun invoke(): Board {
        return repository.resetGame()
    }
}