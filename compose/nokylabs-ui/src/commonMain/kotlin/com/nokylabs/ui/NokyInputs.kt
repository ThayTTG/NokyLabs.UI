package com.nokylabs.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

/** Campo de texto do Kit: cantos de 12, a borda ganha a cor do app quando está em foco. */
@Composable
fun NokyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val c = NokyTheme.colors
    val interacao = remember { MutableInteractionSource() }
    val foco by interacao.collectIsFocusedAsState()
    val hover by interacao.collectIsHoveredAsState()
    val forma = RoundedCornerShape(12.dp)
    val estilo = NokyTheme.type.body
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .alpha(if (enabled) 1f else 0.6f)
            .defaultMinSize(minHeight = 42.dp)
            .hoverable(interacao, enabled),
        enabled = enabled,
        singleLine = singleLine,
        textStyle = estilo,
        cursorBrush = SolidColor(c.accent),
        interactionSource = interacao,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { campo ->
            Box(
                Modifier.clip(forma)
                    .background(if (hover && !foco && enabled) c.surfaceHover else c.surface, forma)
                    .border(1.dp, if (foco) c.accent else c.border, forma)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty() && placeholder.isNotEmpty()) Text(placeholder, style = estilo.copy(color = c.textMuted))
                campo()
            }
        },
    )
}

/**
 * Barra de progresso em pílula na cor do app. `progress = null` mostra a barra "andando"
 * (quando não dá para saber quanto falta). `slim` = 6 de altura.
 */
@Composable
fun NokyProgressBar(progress: Float?, modifier: Modifier = Modifier, slim: Boolean = false) {
    val c = NokyTheme.colors
    val forma = RoundedCornerShape(50)
    BoxWithConstraints(modifier.fillMaxWidth().height(if (slim) 6.dp else 4.dp).clip(forma).background(c.surfaceRaised, forma)) {
        if (progress != null) {
            Box(Modifier.fillMaxHeight().fillMaxWidth(progress.coerceIn(0f, 1f)).clip(forma).background(c.accent, forma))
        } else {
            val t = rememberInfiniteTransition()
            val pos by t.animateFloat(-0.35f, 1f, infiniteRepeatable(tween(1100, easing = LinearEasing), RepeatMode.Restart))
            Box(Modifier.fillMaxHeight().width(maxWidth * 0.35f).offset(x = maxWidth * pos).clip(forma).background(c.accent, forma))
        }
    }
}
