package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

private val POSSIBLE_CELLS = arrayOf(
    Cell(CellState.Hidden(), CellContent.Empty),
    Cell(CellState.Hidden(), CellContent.Mine),
    Cell(CellState.Hidden(isFlagged = true), CellContent.Empty),
    Cell(CellState.Hidden(isFlagged = true), CellContent.Mine),
    Cell(CellState.Revealed(), CellContent.Empty),
    Cell(CellState.Revealed(), CellContent.Number(count = 1)),
    Cell(CellState.Revealed(), CellContent.Number(count = 2)),
    Cell(CellState.Revealed(), CellContent.Number(count = 3)),
    Cell(CellState.Revealed(), CellContent.Number(count = 4)),
    Cell(CellState.Revealed(), CellContent.Number(count = 5)),
    Cell(CellState.Revealed(), CellContent.Number(count = 6)),
    Cell(CellState.Revealed(), CellContent.Number(count = 7)),
    Cell(CellState.Revealed(), CellContent.Number(count = 8)),
    Cell(CellState.Revealed(isExploded = true), CellContent.Mine),
)

@Immutable
data class Cell(
    val state: CellState,
    val content: CellContent,
) {
    val isHidden get() =
        state is CellState.Hidden

    val isFlagged get() =
        (state as? CellState.Hidden)?.isFlagged ?: false

    val isRevealed get() =
        state is CellState.Revealed

    val isExploded get() =
        (state as? CellState.Revealed)?.isExploded ?: false

    companion object {
        fun hidden(content: CellContent = CellContent.Empty) =
            Cell(CellState.Hidden(), content)

        fun random() =
            POSSIBLE_CELLS.random()
    }
}

@Immutable
sealed interface CellState {
    data class Hidden(val isFlagged: Boolean = false) : CellState
    data class Revealed(val isExploded: Boolean = false) : CellState
}

@Immutable
sealed class CellContent {
    data object Empty : CellContent()
    data object Mine : CellContent()
    data class Number(val count: Int) : CellContent()
}
