package com.nokylabs.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.min
import kotlin.math.pow

/**
 * As animações do Kit (as mesmas do ValReport):
 * - `Modifier.nokyEntrance(order = 2)`: o bloco sobe 10 e aparece, um depois do outro (45 ms de diferença).
 * - `NokyCountText(target = total, format = "{0:N0} apps")`: o número conta do zero até o valor.
 *
 * Para desligar tudo (ex.: a pessoa pediu menos movimento), use `NokyAnim.enabled = false`.
 */
object NokyAnim {
    var enabled: Boolean = true

    /** A curva de saída suave do Kit: cubic-bezier(.2, .7, .2, 1). */
    val EaseOut = CubicBezierEasing(0.2f, 0.7f, 0.2f, 1f)
}

/** Entrada em sequência: sobe 10 e aparece, com 45 ms de atraso por posição (até a 16ª). */
fun Modifier.nokyEntrance(order: Int = 0): Modifier = composed {
    if (!NokyAnim.enabled) return@composed this
    val progresso = remember { Animatable(0f) }
    val deslocamento = with(LocalDensity.current) { 10.dp.toPx() }
    LaunchedEffect(Unit) {
        delay(min(order, 16) * 45L)
        progresso.animateTo(1f, tween(450, easing = NokyAnim.EaseOut))
    }
    graphicsLayer {
        alpha = progresso.value
        translationY = (1f - progresso.value) * deslocamento
    }
}

/** Número contando do zero até [target] em 750 ms, no formato brasileiro ("{0:N1}%" → 68,4%). */
@Composable
fun NokyCountText(
    target: Double,
    modifier: Modifier = Modifier,
    format: String = "{0:N0}",
    style: TextStyle = NokyTheme.type.h2,
) {
    val valor = remember { Animatable(if (NokyAnim.enabled) 0f else target.toFloat()) }
    LaunchedEffect(target) {
        if (!NokyAnim.enabled || target == 0.0) { valor.snapTo(target.toFloat()); return@LaunchedEffect }
        valor.snapTo(0f)
        valor.animateTo(target.toFloat(), tween(750, easing = { 1f - (1f - it).pow(3) }))
    }
    val mostrado = if (valor.value == target.toFloat()) target else valor.value.toDouble()
    Text(NokyFormat.formatValue(format, mostrado), modifier, style = style.merge(NokyTheme.type.num))
}
