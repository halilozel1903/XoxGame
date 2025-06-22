package com.halil.ozel.xoxgame.domain.repository

import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player

/**
 * Created by halilozel1903 on 22.06.2025.
 */
interface GameRepository {
    fun getBoard(): Board
    fun makeMove(row: Int, col: Int, player: Player): Board
    fun resetGame(): Board
}