package io.github.suspendfun.minesweeper.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
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
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) { viewModel.pauseTimer() }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { viewModel.resumeTimer() }

    val gameState by viewModel.gameState.collectAsStateWithLifecycle()
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()
    Surface(
        modifier = modifier,
    ) {
        GameScreenContent(
            modifier = Modifier
                .safeDrawingPadding(),
            game = gameState,
            timer = timerState,
            onRestart = viewModel::restart,
        )
    }
}

@Composable
private fun GameScreenContent(
    game: GameUiState,
    timer: TimerUiState,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GameScreenHeader(
            modifier = Modifier
                .fillMaxWidth(),
            minesLeft = game.minesLeft,
            timer = timer.value,
        )
        GameScreenScene(
            modifier = Modifier
                .weight(1f),
            game = game,
        )
        GameScreenFooter(
            modifier = Modifier
                .fillMaxWidth(),
            onRestart = onRestart,
        )
    }
}

@Composable
private fun ColumnScope.GameScreenHeader(
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
private fun ColumnScope.GameScreenFooter(
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
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
    Surface {
        GameScreenContent(
            game = GameUiState.create(DefaultGameConfig),
            timer = TimerUiState.create(999),
            onRestart = {},
        )
    }
}
