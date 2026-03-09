package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

val DefaultGameConfig = GameConfig(
    col = 9,
    row = 9,
    mines = 12,
)

@Immutable
data class GameConfig(
    val col: Int,
    val row: Int,
    val mines: Int,
)
