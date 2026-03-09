package io.github.suspendfun.minesweeper.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.suspendfun.minesweeper.theme.AppTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
fun Label(
    text: String,
    modifier: Modifier = Modifier,
    referenceText: String? = null,
    icon: DrawableResource? = null,
    color: Color = AppTheme.colors.onBackground,
) {
    val style = rememberStyle(color)
    val minTextWidth = rememberMinTextWidth(referenceText, style)
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Image(
                bitmap = imageResource(icon),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(16.dp),
            )
        }
        BasicText(
            text = text,
            style = style,
            modifier = minTextWidth?.let { Modifier.widthIn(min = it) } ?: Modifier,
        )
    }
}

@Composable
private fun rememberMinTextWidth(
    measureText: String?,
    style: TextStyle,
): Dp? {
    if (measureText == null) {
        return null
    }
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    return remember(measureText, style) {
        with(density) {
            textMeasurer.measure(measureText, style).size.width.toDp()
        }
    }
}

@Composable
private fun rememberStyle(color: Color): TextStyle {
    val labelStyle = AppTheme.typography.label
    return remember(labelStyle, color) { labelStyle.copy(color = color) }
}
