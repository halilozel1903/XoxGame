package com.halil.ozel.xoxgame.domain.model

enum class Player {
    X, O;

    val opponent: Player
        get() = if (this == X) O else X
}
