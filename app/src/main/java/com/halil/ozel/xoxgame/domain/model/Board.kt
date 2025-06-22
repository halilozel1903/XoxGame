package com.halil.ozel.xoxgame.domain.model

/**
 * Created by halilozel1903 on 22.06.2025.
 */
data class Board(
    val cells: List<List<Player?>>
) {
    companion object {
        fun empty() = Board(List(3) { List<Player?>(3) { null } })
    }
}