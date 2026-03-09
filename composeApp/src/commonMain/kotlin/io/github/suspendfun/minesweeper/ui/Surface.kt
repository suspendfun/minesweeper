package io.github.suspendfun.minesweeper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.suspendfun.minesweeper.theme.AppTheme

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colors.background,
    content: @Composable () -> Unit,
) {
    Box(
        modifier
            .background(color),
    ) {
        content()
    }
}
