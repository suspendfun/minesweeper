package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

val DefaultGameConfig = GameConfig(
    columns = 9,
    rows = 9,
    mines = 12,
)

@Immutable
data class GameConfig(
    val columns: Int,
    val rows: Int,
    val mines: Int,
)
