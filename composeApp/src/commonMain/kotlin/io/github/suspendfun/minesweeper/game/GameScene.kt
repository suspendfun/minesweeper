package io.github.suspendfun.minesweeper.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import minesweeper.composeapp.generated.resources.Res
import minesweeper.composeapp.generated.resources.cell_exploded
import minesweeper.composeapp.generated.resources.cell_hidden
import minesweeper.composeapp.generated.resources.cell_revealed
import minesweeper.composeapp.generated.resources.flag
import minesweeper.composeapp.generated.resources.mine
import minesweeper.composeapp.generated.resources.number_1
import minesweeper.composeapp.generated.resources.number_2
import minesweeper.composeapp.generated.resources.number_3
import minesweeper.composeapp.generated.resources.number_4
import minesweeper.composeapp.generated.resources.number_5
import minesweeper.composeapp.generated.resources.number_6
import minesweeper.composeapp.generated.resources.number_7
import minesweeper.composeapp.generated.resources.number_8
import org.jetbrains.compose.resources.imageResource
import kotlin.math.min
import kotlin.math.roundToInt

private val CellSpacing: Dp = 1.dp

@Immutable
private data class CellSprites(
    val hidden: ImageBitmap,
    val revealed: ImageBitmap,
    val exploded: ImageBitmap,
    val flag: ImageBitmap,
    val mine: ImageBitmap,
    val numbers: List<ImageBitmap>,
)

@Composable
private fun rememberCellSprites(): CellSprites {
    val hidden = imageResource(Res.drawable.cell_hidden)
    val revealed = imageResource(Res.drawable.cell_revealed)
    val exploded = imageResource(Res.drawable.cell_exploded)
    val flag = imageResource(Res.drawable.flag)
    val mine = imageResource(Res.drawable.mine)
    val n1 = imageResource(Res.drawable.number_1)
    val n2 = imageResource(Res.drawable.number_2)
    val n3 = imageResource(Res.drawable.number_3)
    val n4 = imageResource(Res.drawable.number_4)
    val n5 = imageResource(Res.drawable.number_5)
    val n6 = imageResource(Res.drawable.number_6)
    val n7 = imageResource(Res.drawable.number_7)
    val n8 = imageResource(Res.drawable.number_8)
    return remember {
        CellSprites(
            hidden = hidden,
            revealed = revealed,
            exploded = exploded,
            flag = flag,
            mine = mine,
            numbers = listOf(n1, n2, n3, n4, n5, n6, n7, n8),
        )
    }
}

@Composable
fun GameScreenScene(
    game: GameUiState,
    modifier: Modifier = Modifier,
) {
    val sprites = rememberCellSprites()

    Canvas(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val spacingPx = CellSpacing.toPx()
        val cellSize = min(
            (size.width - spacingPx * (game.board.columns - 1)) / game.board.columns,
            (size.height - spacingPx * (game.board.rows - 1)) / game.board.rows,
        ).roundToInt()

        val gridW = cellSize * game.board.columns + spacingPx * (game.board.columns - 1)
        val gridH = cellSize * game.board.rows + spacingPx * (game.board.rows - 1)
        val originX = (size.width - gridW) / 2f
        val originY = (size.height - gridH) / 2f
        val cellDstSize = IntSize(cellSize, cellSize)
        val cellStep = cellSize + spacingPx

        game.board.forEach { col, row, cell ->
            val dstOffset = IntOffset(
                x = (originX + col * cellStep).roundToInt(),
                y = (originY + row * cellStep).roundToInt(),
            )
            drawCell(cell, dstOffset, cellDstSize, sprites)
        }
    }
}

private fun DrawScope.drawCell(
    cell: Cell,
    dstOffset: IntOffset,
    dstSize: IntSize,
    sprites: CellSprites,
) {
    val background = when (cell.state) {
        CellState.Hidden -> sprites.hidden
        CellState.Revealed -> sprites.revealed
        CellState.Exploded -> sprites.exploded
    }
    drawImage(background, dstOffset = dstOffset, dstSize = dstSize, filterQuality = FilterQuality.None)

    val overlay = when (val content = cell.content) {
        is CellContent.Empty -> null
        is CellContent.Flag -> sprites.flag
        is CellContent.Mine -> sprites.mine
        is CellContent.Number -> sprites.numbers[content.count - 1]
    }
    if (overlay != null) {
        drawImage(overlay, dstOffset = dstOffset, dstSize = dstSize, filterQuality = FilterQuality.None)
    }
}
