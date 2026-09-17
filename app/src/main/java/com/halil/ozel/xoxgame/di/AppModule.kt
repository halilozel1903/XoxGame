package com.halil.ozel.xoxgame.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.halil.ozel.xoxgame.data.GameRepositoryImpl
import com.halil.ozel.xoxgame.domain.repository.GameRepository
import com.halil.ozel.xoxgame.domain.usecase.GetBoardUseCase
import com.halil.ozel.xoxgame.domain.usecase.MakeMoveUseCase
import com.halil.ozel.xoxgame.domain.usecase.ResetGameUseCase
import com.halil.ozel.xoxgame.presentation.viewmodel.GameViewModel

object AppModule {
    private val gameRepository: GameRepository = GameRepositoryImpl()

    val getBoardUseCase = GetBoardUseCase(gameRepository)
    val makeMoveUseCase = MakeMoveUseCase(gameRepository)
    val resetGameUseCase = ResetGameUseCase(gameRepository)

    val gameViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            GameViewModel(
                getBoardUseCase = getBoardUseCase,
                makeMoveUseCase = makeMoveUseCase,
                resetGameUseCase = resetGameUseCase
            )
        }
    }
}
