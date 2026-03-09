package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

private val POSSIBLE_CELLS = arrayOf(
    Cell(CellState.Hidden, CellContent.Empty),
    Cell(CellState.Hidden, CellContent.Mine),
    Cell(CellState.Hidden, CellContent.Flag),
    Cell(CellState.Revealed, CellContent.Empty),
    Cell(CellState.Revealed, CellContent.Number(count = 1)),
    Cell(CellState.Revealed, CellContent.Number(count = 2)),
    Cell(CellState.Revealed, CellContent.Number(count = 3)),
    Cell(CellState.Revealed, CellContent.Number(count = 4)),
    Cell(CellState.Revealed, CellContent.Number(count = 5)),
    Cell(CellState.Revealed, CellContent.Number(count = 6)),
    Cell(CellState.Revealed, CellContent.Number(count = 7)),
    Cell(CellState.Revealed, CellContent.Number(count = 8)),
)

@Immutable
data class Cell(
    val state: CellState,
    val content: CellContent,
) {
    companion object {
        fun hidden() = Cell(CellState.Hidden, CellContent.Empty)

        fun random() = POSSIBLE_CELLS.random()
    }
}

enum class CellState {
    Hidden,
    Revealed,
    Exploded,
}

@Immutable
sealed class CellContent {
    data object Empty : CellContent()
    data object Mine : CellContent()
    data object Flag : CellContent()
    data class Number(val count: Int) : CellContent()
}
