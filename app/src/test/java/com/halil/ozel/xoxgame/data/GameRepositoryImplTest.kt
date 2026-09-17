package com.halil.ozel.xoxgame.data

import com.halil.ozel.xoxgame.domain.model.Board
import com.halil.ozel.xoxgame.domain.model.Player
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GameRepositoryImplTest {

    @Test
    fun makeMovePlacesPlayerAndIgnoresOccupiedCells() {
        val repository = GameRepositoryImpl()

        val afterFirst = repository.makeMove(0, 0, Player.X)
        assertEquals(Player.X, afterFirst.cell(0, 0))

        val afterIgnored = repository.makeMove(0, 0, Player.O)
        assertEquals(Player.X, afterIgnored.cell(0, 0))
    }

    @Test
    fun resetGameClearsBoard() {
        val repository = GameRepositoryImpl()
        repository.makeMove(1, 1, Player.O)

        val reset = repository.resetGame()
        assertNull(reset.cell(1, 1))
        assertEquals(Board.empty(), reset)
    }
}
