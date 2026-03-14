package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

enum class BoardState {
    Idle,
    Active,
}

@Immutable
class Board private constructor(
    private val config: GameConfig,
    private val state: BoardState,
    init: (col: Int, row: Int) -> Cell,
) {
    private val cells: List<Cell> =
        List(config.columns * config.rows) { index ->
            init(index % config.columns, index / config.columns)
        }

    val columns: Int = config.columns

    val rows: Int = config.rows

    operator fun get(col: Int, row: Int): Cell {
        require(isInBounds(col, row))
        return cells[indexOf(col, row)]
    }

    fun forEach(action: (col: Int, row: Int, cell: Cell) -> Unit) {
        for (row in 0 until config.rows) {
            for (col in 0 until config.columns) {
                action(col, row, cells[indexOf(col, row)])
            }
        }
    }

    fun reveal(col: Int, row: Int): Board {
        require(isInBounds(col, row))
        val cell = this[col, row]
        if (cell.isRevealed || cell.isFlagged) {
            return this
        }
        return if (state == BoardState.Idle) {
            activate(col, row)
        } else {
            when (cell.content) {
                CellContent.Empty -> {
                    revealCascading(col, row)
                }
                CellContent.Mine -> {
                    explode(col, row)
                }
                else -> {
                    revealOne(col, row)
                }
            }
        }
    }

    fun flag(col: Int, row: Int): Board {
        require(isInBounds(col, row))
        val cell = this[col, row]
        if (!cell.isHidden) {
            return this
        }
        return Board(config, state) { c, r ->
            if (c == col && r == row) {
                val state = CellState.Hidden(isFlagged = !cell.isFlagged)
                Cell(
                    state = state,
                    content = cell.content,
                )
            } else {
                this[c, r]
            }
        }
    }

    private fun activate(col: Int, row: Int): Board {
        // TODO: Implement activate logic
        return this
    }

    private fun explode(col: Int, row: Int): Board {
        // TODO: Implement explode logic
        return this
    }

    private fun revealOne(col: Int, row: Int): Board {
        // TODO: Implement revealOne logic
        return this
    }

    private fun revealCascading(col: Int, row: Int): Board {
        // TODO: Implement revealCascading logic
        return this
    }

    @Suppress("UnusedPrivateMember")
    private fun neighbors(col: Int, row: Int): List<Coordinate> {
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

    private fun isInBounds(col: Int, row: Int): Boolean =
        col >= 0 && col < config.columns && row >= 0 && row < config.rows

    private fun indexOf(col: Int, row: Int): Int =
        col + row * config.columns

    companion object {
        fun create(config: GameConfig): Board =
            Board(config, BoardState.Idle) { _, _ -> Cell.hidden() }
    }
}
