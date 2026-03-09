package io.github.suspendfun.minesweeper.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppColors = staticCompositionLocalOf { DefaultColors }

val LocalAppTypography = staticCompositionLocalOf { DefaultTypography }

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val typography = rememberTypography()
    CompositionLocalProvider(
        LocalAppColors provides DefaultColors,
        LocalAppTypography provides typography,
        content = content,
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current
}
