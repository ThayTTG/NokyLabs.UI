package com.nokylabs.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Filtro em pílula; selecionado ganha a cor do app. */
@Composable
fun NokyChip(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = NokyTheme.colors
    val interacao = remember { MutableInteractionSource() }
    val hover by interacao.collectIsHoveredAsState()
    val fundo by animateColorAsState(
        when { selected -> c.accent; hover -> c.surfaceHover; else -> c.surface }, tween(150))
    val texto = when { selected -> c.onAccent; hover -> c.text; else -> c.textSecondary }
    val forma = RoundedCornerShape(50)
    Box(
        modifier.clip(forma).background(fundo, forma)
            .border(1.dp, if (selected) c.accent else c.border, forma)
            .hoverable(interacao)
            .selectable(selected, interacao, indication = null, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 7.dp),
    ) {
        Text(text, style = NokyTheme.type.body.copy(fontSize = 13.sp, fontWeight = FontWeight.Medium, color = texto))
    }
}

/** Seletor segmentado: `NokySegmented(listOf("Dia", "Semana", "Mês"), selecionado) { selecionado = it }`. */
@Composable
fun NokySegmented(options: List<String>, selected: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    val c = NokyTheme.colors
    val forma = RoundedCornerShape(50)
    Row(
        modifier.clip(forma).background(c.surface, forma).border(1.dp, c.border, forma).padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        options.forEachIndexed { i, opcao ->
            val marcado = i == selected
            val interacao = remember { MutableInteractionSource() }
            val hover by interacao.collectIsHoveredAsState()
            val fundo by animateColorAsState(if (marcado) c.surfaceRaised else Color.Transparent, tween(150))
            Box(
                Modifier.clip(forma).background(fundo, forma)
                    .hoverable(interacao)
                    .selectable(marcado, interacao, indication = null, role = Role.RadioButton) { onSelect(i) }
                    .padding(horizontal = 12.dp, vertical = 5.dp),
            ) {
                Text(opcao, style = NokyTheme.type.body.copy(
                    fontSize = 12.5.sp, fontWeight = FontWeight.Medium,
                    color = if (marcado || hover) c.text else c.textSecondary))
            }
        }
    }
}

/** Item da navegação lateral (ou de uma lista de abas): o ativo ganha fundo na cor do app. */
@Composable
fun NokyNavItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    val c = NokyTheme.colors
    val interacao = remember { MutableInteractionSource() }
    val hover by interacao.collectIsHoveredAsState()
    val fundo by animateColorAsState(
        when { selected -> c.accentSoft; hover -> c.surface; else -> Color.Transparent }, tween(150))
    val forma = RoundedCornerShape(10.dp)
    Row(
        modifier.fillMaxWidth().clip(forma).background(fundo, forma)
            .hoverable(interacao)
            .selectable(selected, interacao, indication = null, role = Role.Tab, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (icon != null) Icon(icon, null, Modifier.size(18.dp), tint = if (selected) c.accentText else c.textMuted)
        Text(text, style = NokyTheme.type.body.copy(
            fontWeight = FontWeight.Medium,
            color = if (selected || hover) c.text else c.textSecondary))
    }
}
