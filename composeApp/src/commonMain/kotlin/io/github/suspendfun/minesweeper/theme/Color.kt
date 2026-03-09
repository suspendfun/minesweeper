@file:Suppress("MatchingDeclarationName", "MagicNumber")

package io.github.suspendfun.minesweeper.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val background: Color,
    val onBackground: Color,
)

val DefaultColors = AppColors(
    background = Color(0xFF747474),
    onBackground = Color(0xFFFFFFFF),
)
