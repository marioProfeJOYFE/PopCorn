package com.mrh.popcorn.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    blurRadius: Dp = 12.dp,
    backgroundAlpha: Float = 0.15f,
    borderAlpha: Float = 0.3f,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    // Usamos los colores dinámicos de Material 3
    val surfaceColor = MaterialTheme.colorScheme.surface
    val borderColor = MaterialTheme.colorScheme.outlineVariant

    Box(modifier = modifier.clip(shape)) {
        // 1. Capa de fondo con el efecto de desenfoque y gradientes
        Box(
            modifier = Modifier
                .matchParentSize() // Se ajusta al tamaño exacto del contenido
                .blur(radius = blurRadius)
                .background(brush = createGlassGradient(surfaceColor, backgroundAlpha))
                .border(
                    width = borderWidth,
                    brush = createGlassBorderGradient(borderColor, borderAlpha),
                    shape = shape
                )
        )

        // 2. Capa de contenido (permanece 100% nítida)
        content()
    }
}

@Composable
fun LightGlassContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    GlassContainer(
        modifier = modifier,
        blurRadius = 6.dp,
        backgroundAlpha = 0.06f,
        borderAlpha = 0.12f,
        content = content
    )
}

@Composable
fun HeavyGlassContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    GlassContainer(
        modifier = modifier,
        blurRadius = 16.dp,
        backgroundAlpha = 0.12f,
        borderAlpha = 0.25f,
        content = content
    )
}