package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

@Immutable
class Board private constructor(
    private val config: GameConfig,
) {
    private val cells: List<Cell> =
        List(config.columns * config.rows) { Cell.hidden() }

    val columns: Int =
        config.columns

    val rows: Int =
        config.rows

    fun isInBounds(col: Int, row: Int): Boolean =
        col >= 0 && col < config.columns && row >= 0 && row < config.rows

    fun indexOf(col: Int, row: Int): Int =
        col + row * config.columns

    fun neighbors(col: Int, row: Int): List<Coordinate> {
        require(isInBounds(col, row))
        return buildList {
            for (deltaCol in -1..1) {
                for (deltaRow in -1..1) {
                    if (deltaCol == 0 && deltaRow == 0) {
                        continue
                    }
                    val newCol = col + deltaCol
                    val newRow = row + deltaRow
                    if (isInBounds(newCol, newRow)) {
                        add(Coordinate(newCol, newRow))
                    }
                }
            }
        }
    }

    fun forEach(action: (col: Int, row: Int, cell: Cell) -> Unit) {
        for (row in 0 until config.rows) {
            for (col in 0 until config.columns) {
                action(col, row, cells[indexOf(col, row)])
            }
        }
    }

    operator fun get(col: Int, row: Int): Cell {
        require(isInBounds(col, row))
        return cells[indexOf(col, row)]
    }

    class Builder(
        private val config: GameConfig,
    ) {
        fun build() =
            Board(config)
    }
}
