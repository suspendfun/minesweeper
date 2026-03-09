package io.github.suspendfun.minesweeper.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import minesweeper.composeapp.generated.resources.Res
import minesweeper.composeapp.generated.resources.cell_hidden
import org.jetbrains.compose.resources.imageResource
import kotlin.math.min
import kotlin.math.roundToInt

private val CellSpacing: Dp = 1.dp

@Composable
fun GameScreenScene(
    game: GameUiState,
    modifier: Modifier = Modifier,
) {
    val cellHidden = imageResource(Res.drawable.cell_hidden)

    Canvas(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val spacingPx = CellSpacing.toPx()
        val cellSize = min(
            (size.width - spacingPx * (game.board.col - 1)) / game.board.col,
            (size.height - spacingPx * (game.board.row - 1)) / game.board.row,
        ).roundToInt()

        val gridW = cellSize * game.board.col + spacingPx * (game.board.col - 1)
        val gridH = cellSize * game.board.row + spacingPx * (game.board.row - 1)
        val originX = (size.width - gridW) / 2f
        val originY = (size.height - gridH) / 2f
        val cellDstSize = IntSize(cellSize, cellSize)

        for (row in 0 until game.board.row) {
            for (col in 0 until game.board.col) {
                val x = originX + col * (cellSize + spacingPx)
                val y = originY + row * (cellSize + spacingPx)
                drawImage(
                    image = cellHidden,
                    dstOffset = IntOffset(x.roundToInt(), y.roundToInt()),
                    dstSize = cellDstSize,
                    filterQuality = FilterQuality.None,
                )
            }
        }
    }
}
