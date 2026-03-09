package io.github.suspendfun.minesweeper.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import minesweeper.composeapp.generated.resources.Res
import minesweeper.composeapp.generated.resources.overlay_hover
import org.jetbrains.compose.resources.imageResource

@Composable
fun rememberOverlayHoverPaint(): Paint {
    val image = imageResource(Res.drawable.overlay_hover)
    return remember(image) {
        Paint().apply {
            shader = ImageShader(image, TileMode.Repeated, TileMode.Repeated)
            blendMode = BlendMode.Multiply
        }
    }
}

fun DrawScope.drawOverlayHover(rect: Rect, paint: Paint) {
    drawIntoCanvas { canvas ->
        canvas.drawRect(rect, paint)
    }
}
