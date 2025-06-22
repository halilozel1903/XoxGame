package com.halil.ozel.xoxgame.di

import com.halil.ozel.xoxgame.data.GameRepositoryImpl
import com.halil.ozel.xoxgame.domain.repository.GameRepository
import com.halil.ozel.xoxgame.domain.usecase.GetBoardUseCase
import com.halil.ozel.xoxgame.domain.usecase.MakeMoveUseCase
import com.halil.ozel.xoxgame.domain.usecase.ResetGameUseCase

/**
 * Created by halilozel1903 on 22.06.2025.
 */

object AppModule {
    private val gameRepository: GameRepository = GameRepositoryImpl()
    val getBoardUseCase = GetBoardUseCase(gameRepository)
    val makeMoveUseCase = MakeMoveUseCase(gameRepository)
    val resetGameUseCase = ResetGameUseCase(gameRepository)
}