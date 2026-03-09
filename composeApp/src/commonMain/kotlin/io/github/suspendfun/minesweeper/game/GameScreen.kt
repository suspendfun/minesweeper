package io.github.suspendfun.minesweeper.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.suspendfun.minesweeper.ui.Button
import io.github.suspendfun.minesweeper.ui.Label
import io.github.suspendfun.minesweeper.ui.Surface
import minesweeper.composeapp.generated.resources.Res
import minesweeper.composeapp.generated.resources.mines_left
import minesweeper.composeapp.generated.resources.restart
import minesweeper.composeapp.generated.resources.timer
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(),
) {
    val uiState by viewModel.gameState.collectAsStateWithLifecycle()
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()
    Surface(
        modifier = modifier,
    ) {
        GameScreenContent(
            modifier = Modifier
                .safeDrawingPadding(),
            minesLeft = uiState.minesLeft,
            timer = timerState.timer,
            onRestart = viewModel::restart,
        )
    }
}

@Composable
private fun GameScreenContent(
    minesLeft: String,
    timer: String,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
    ) {
        GameScreenHeader(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            minesLeft = minesLeft,
            timer = timer,
        )
        GameScreenScene(
            modifier = Modifier
                .align(Alignment.Center),
        )
        GameScreenFooter(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onRestart = onRestart,
        )
    }
}

@Composable
private fun GameScreenHeader(
    minesLeft: String,
    timer: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Label(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(),
            text = minesLeft,
            referenceText = "00",
            icon = Res.drawable.mines_left,
        )
        Label(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(),
            text = timer,
            referenceText = "00:00",
            icon = Res.drawable.timer,
        )
    }
}

@Composable
private fun GameScreenFooter(
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Button(
            text = stringResource(Res.string.restart),
            onClick = onRestart,
        )
    }
}

@Preview
@Composable
private fun GameScreenPreview() {
    val state = GameUiState()
    Surface {
        GameScreenContent(
            minesLeft = state.minesLeft.toString(),
            timer = TimerUiState().timer,
            onRestart = {},
        )
    }
}
