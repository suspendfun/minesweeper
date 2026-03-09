package io.github.suspendfun.minesweeper

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.suspendfun.minesweeper.game.GameScreen
import io.github.suspendfun.minesweeper.theme.AppTheme

@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    AppTheme {
        GameScreen(
            modifier = modifier
                .fillMaxSize(),
        )
    }
}
