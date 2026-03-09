package io.github.suspendfun.minesweeper.game

import androidx.compose.runtime.Immutable

@Immutable
data class Cell(
    val state: CellState,
    val content: CellContent,
) {
    companion object {
        fun hidden() = Cell(CellState.Hidden, CellContent.Empty)
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
