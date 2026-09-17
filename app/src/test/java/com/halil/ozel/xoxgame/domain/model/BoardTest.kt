package com.halil.ozel.xoxgame.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class BoardTest {

    @Test
    fun emptyBoardHasNoWinnerOrDraw() {
        val board = Board.empty()
        assertNull(board.winner)
        assertFalse(board.isDraw)
        assertFalse(board.hasMarks)
    }

    @Test
    fun detectsRowWin() {
        val board = boardOf(
            listOf(Player.X, Player.X, Player.X),
            listOf(Player.O, Player.O, null),
            listOf(null, null, null)
        )
        assertEquals(Player.X, board.winner)
        assertFalse(board.isDraw)
    }

    @Test
    fun detectsColumnWin() {
        val board = boardOf(
            listOf(Player.O, Player.X, null),
            listOf(Player.O, Player.X, null),
            listOf(Player.O, null, Player.X)
        )
        assertEquals(Player.O, board.winner)
    }

    @Test
    fun detectsDiagonalWin() {
        val board = boardOf(
            listOf(Player.X, Player.O, null),
            listOf(Player.O, Player.X, null),
            listOf(null, null, Player.X)
        )
        assertEquals(Player.X, board.winner)
    }

    @Test
    fun detectsDraw() {
        val board = boardOf(
            listOf(Player.X, Player.O, Player.X),
            listOf(Player.X, Player.O, Player.O),
            listOf(Player.O, Player.X, Player.X)
        )
        assertNull(board.winner)
        assertTrue(board.isDraw)
        assertTrue(board.isFull)
    }

    private fun boardOf(vararg rows: List<Player?>) = Board(rows.toList())
}
