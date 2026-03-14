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
        require(isInBounds(col, row) && canReveal(col, row))
        return if (state == BoardState.Idle) {
            activate(col, row)
        } else {
            when (this[col, row].content) {
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
        require(isInBounds(col, row) && isHidden(col, row))
        return withCell(col, row) {
            it.copy(state = CellState.Hidden(isFlagged = !it.isFlagged))
        }
    }

    private fun activate(col: Int, row: Int): Board {
        // TODO: Implement activate
        return this
    }

    private fun explode(col: Int, row: Int): Board =
        withCell(col, row) { it.copy(state = CellState.Revealed(isExploded = true)) }

    private fun revealOne(col: Int, row: Int): Board =
        withCell(col, row) { it.copy(state = CellState.Revealed()) }

    private fun revealCascading(col: Int, row: Int): Board {
        val visited = mutableSetOf<Coordinate>()
        val queue = ArrayDeque<Coordinate>()
        queue.add(Coordinate(col, row))

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val (currentCol, currentRow) = current

            if (!visited.add(current)) {
                continue
            }

            if (isEmpty(currentCol, currentRow)) {
                val unvisitedNeighbors = neighbors(currentCol, currentRow)
                    .filter { (neighborCol, neighborRow) ->
                        canReveal(neighborCol, neighborRow)
                    }
                queue.addAll(unvisitedNeighbors)
            }
        }

        return withCells(visited) {
            it.copy(state = CellState.Revealed())
        }
    }

    private fun withCell(col: Int, row: Int, transform: (Cell) -> Cell): Board =
        Board(config, state) { c, r ->
            if (c == col && r == row) transform(this[c, r]) else this[c, r]
        }

    private fun withCells(coordinates: Set<Coordinate>, transform: (Cell) -> Cell): Board =
        Board(config, state) { c, r ->
            if (Coordinate(c, r) in coordinates) transform(this[c, r]) else this[c, r]
        }

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

    private fun isHidden(col: Int, row: Int): Boolean =
        this[col, row].isHidden

    private fun isFlagged(col: Int, row: Int): Boolean =
        this[col, row].isFlagged

    private fun isRevealed(col: Int, row: Int): Boolean =
        this[col, row].isRevealed

    private fun isExploded(col: Int, row: Int): Boolean =
        this[col, row].isExploded

    private fun isEmpty(col: Int, row: Int): Boolean =
        this[col, row].content == CellContent.Empty

    private fun canReveal(col: Int, row: Int): Boolean =
        isHidden(col, row) && !isFlagged(col, row)

    private fun indexOf(col: Int, row: Int): Int =
        col + row * config.columns

    companion object {
        fun create(config: GameConfig): Board =
            Board(config, BoardState.Idle) { _, _ -> Cell.hidden() }
    }
}
