package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

@Immutable
class Board private constructor(
    private val config: GameConfig,
) {
    private val cells: List<Cell> = List(config.columns * config.rows) { Cell.hidden() }

    val columns: Int = config.columns

    val rows: Int = config.rows

    operator fun get(col: Int, row: Int): Cell {
        require(col >= 0 && row >= 0)
        require(col < config.columns && row < config.rows)
        return cells[col + row * config.columns]
    }

    class Builder(
        private val config: GameConfig,
    ) {
        fun build() =
            Board(config)
    }
}
