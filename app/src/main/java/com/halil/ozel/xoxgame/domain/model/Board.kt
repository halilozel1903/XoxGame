package com.halil.ozel.xoxgame.domain.model

data class Board(
    val cells: List<List<Player?>>
) {
    init {
        require(cells.size == SIZE && cells.all { it.size == SIZE }) {
            "Board must be a ${SIZE}x${SIZE} grid"
        }
    }

    fun cell(row: Int, col: Int): Player? = cells[row][col]

    val winner: Player?
        get() {
            for (index in 0 until SIZE) {
                cells[index][0]
                    ?.takeIf { it == cells[index][1] && it == cells[index][2] }
                    ?.let { return it }
                cells[0][index]
                    ?.takeIf { it == cells[1][index] && it == cells[2][index] }
                    ?.let { return it }
            }
            cells[1][1]?.let { center ->
                if (center == cells[0][0] && center == cells[2][2]) return center
                if (center == cells[0][2] && center == cells[2][0]) return center
            }
            return null
        }

    val isFull: Boolean
        get() = cells.all { row -> row.all { it != null } }

    val isDraw: Boolean
        get() = winner == null && isFull

    val hasMarks: Boolean
        get() = cells.any { row -> row.any { it != null } }

    companion object {
        const val SIZE = 3

        fun empty() = Board(List(SIZE) { List(SIZE) { null } })
    }
}
