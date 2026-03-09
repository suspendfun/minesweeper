package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

@Immutable
class Board private constructor(
    private val config: GameConfig,
) {
    private val cells: List<Cell> = List(config.col * config.row) { Cell.hidden() }

    val col: Int = config.col

    val row: Int = config.row

    operator fun get(col: Int, row: Int): Cell {
        require(col in 0 until config.col)
        require(row in 0 until config.row)
        return cells[col + row * config.col]
    }

    class Builder(
        private val config: GameConfig,
    ) {
        fun build() =
            Board(config)
    }
}
