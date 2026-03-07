@file:Suppress("MatchingDeclarationName", "MagicNumber")

package io.github.suspendfun.minesweeper.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val error: Color,
    val outline: Color,
)

val DefaultColors = AppColors(
    background = Color(0xFF282828),
    onBackground = Color(0xFFebdbb2),
    surface = Color(0xFF3c3836),
    onSurface = Color(0xFFd5c4a1),
    surfaceVariant = Color(0xFF504945),
    onSurfaceVariant = Color(0xFFbdae93),
    primary = Color(0xFF83a598),
    onPrimary = Color(0xFF282828),
    error = Color(0xFFfb4934),
    outline = Color(0xFF665c54),
)
