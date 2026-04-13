package com.mrh.popcorn.ui.theme

import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ShaderBrush
import org.intellij.lang.annotations.Language

@Language("AGSL")
const val NOISE_SHADER = """
    uniform float intensity;
    
    // Función matemática para generar ruido pseudoaleatorio
    float random(float2 st) {
        return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);
    }
    
    half4 main(float2 fragCoord) {
        // Obtenemos un valor de ruido entre 0.0 y 1.0 basado en la coordenada del píxel
        float n = random(fragCoord);
        
        // Retornamos un color gris con una opacidad controlada por 'intensity'
        // half4(R, G, B, Alpha)
        return half4(n, n, n, intensity);
    }
"""

@Composable
fun CalculatedGranularBackground(
    modifier: Modifier = Modifier,
    noiseIntensity: Float = 0.15f, // Controla qué tan fuerte es el granulado
    content: @Composable () -> Unit
) {
    // 1. Colores de Material 3
    val colorPrimary = MaterialTheme.colorScheme.primary
    val colorSecondary = MaterialTheme.colorScheme.secondary
    val colorTertiary = MaterialTheme.colorScheme.tertiary

    // 2. Degradado base
    val gradientBrush = remember(colorPrimary, colorSecondary, colorTertiary) {
        Brush.linearGradient(
            colors = listOf(colorPrimary, colorSecondary, colorTertiary),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    }

    // 3. Preparamos el Shader Brush (Solo para Android 13+)
    val noiseBrush = remember(noiseIntensity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val shader = RuntimeShader(NOISE_SHADER).apply {
                setFloatUniform("intensity", noiseIntensity)
            }
            ShaderBrush(shader)
        } else {
            null
        }
    }

    // 4. Contenedor principal
    Box(modifier = modifier.fillMaxSize()) {

        // Dibujamos el fondo en un Canvas para mantenerlo detrás del contenido
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Dibujamos el degradado primero
            drawRect(brush = gradientBrush)

            // Si el dispositivo soporta shaders, dibujamos el ruido encima
            if (noiseBrush != null) {
                drawRect(
                    brush = noiseBrush,
                    blendMode = BlendMode.Overlay // Mezcla el ruido con el degradado
                )
            }
        }

        // 5. Contenido de la UI (botones, textos, tarjetas Glass, etc.)
        content()
    }
}