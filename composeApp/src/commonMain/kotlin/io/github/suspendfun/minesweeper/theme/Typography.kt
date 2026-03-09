@file:Suppress("MatchingDeclarationName")

package io.github.suspendfun.minesweeper.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import minesweeper.composeapp.generated.resources.Res
import minesweeper.composeapp.generated.resources.raster_forge_regular
import org.jetbrains.compose.resources.Font

@Immutable
data class AppTypography(
    val label: TextStyle,
)

val DefaultTypography = AppTypography(
    label = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
    ),
)

@Composable
fun rememberTypography(): AppTypography {
    val font = Font(Res.font.raster_forge_regular, weight = FontWeight.Normal)
    return remember(font) {
        AppTypography(
            label = DefaultTypography.label.copy(fontFamily = FontFamily(font)),
        )
    }
}
