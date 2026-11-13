package com.nokylabs.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Os tipos de botão do Kit (as classes `primary`, `secondary`… do Avalonia). */
enum class NokyButtonKind {
    /** A cor do app, cheia. A ação principal da tela: **uma por tela**. */
    Primary,
    /** Superfície com borda; no hover a borda ganha a cor do app. */
    Secondary,
    /** A cor do app translúcida (ações que não são a principal). */
    Soft,
    /** Âmbar (Atualizar). */
    Warn,
    /** Só contorno. */
    Ghost,
    /** Apagar, desinstalar. Use com ícone e texto claro ("Apagar de vez"), nunca só a cor. */
    Danger,
    /** Algo em andamento (verde); com o mouse por cima vira vermelho ("Encerrar"). */
    Running,
}

private data class CoresBotao(val fundo: Color, val texto: Color, val borda: Color?)

@Composable
private fun coresDoBotao(kind: NokyButtonKind, hover: Boolean, pressionado: Boolean, enabled: Boolean): CoresBotao {
    val c = NokyTheme.colors
    if (!enabled) return CoresBotao(c.surfaceRaised, c.textMuted, if (kind == NokyButtonKind.Secondary || kind == NokyButtonKind.Ghost) c.border else null)
    return when (kind) {
        NokyButtonKind.Primary -> CoresBotao(
            when { pressionado -> c.accentPressed; hover -> c.accentHover; else -> c.accent }, c.onAccent, null)
        NokyButtonKind.Secondary ->
            if (hover) CoresBotao(c.surfaceHover, c.accentText, c.accent) else CoresBotao(c.surface, c.text, c.border)
        NokyButtonKind.Soft ->
            if (hover) CoresBotao(c.accentSoftHover, c.text, null) else CoresBotao(c.accentSoft, c.accentText, null)
        NokyButtonKind.Warn ->
            CoresBotao(if (hover) c.neutrals.warningSoftHover else c.neutrals.warningSoft, c.warning, null)
        NokyButtonKind.Ghost ->
            if (hover) CoresBotao(c.surfaceHover, c.text, c.accentSoftHover) else CoresBotao(Color.Transparent, c.textSecondary, c.border)
        NokyButtonKind.Danger ->
            if (hover) CoresBotao(c.danger, Color.White, null) else CoresBotao(c.neutrals.dangerSoft, c.danger, null)
        NokyButtonKind.Running ->
            if (hover) CoresBotao(c.danger, Color.White, null) else CoresBotao(c.neutrals.successSoft, c.success, null)
    }
}

/**
 * Botão do Kit: pílula, negrito, encolhe um pouquinho ao tocar.
 *
 * ```kotlin
 * NokyButton("Agendar tarefa", onClick = { … })                       // primary
 * NokyButton("Apagar de vez", { … }, kind = NokyButtonKind.Danger, icon = NokyIcons.Trash)
 * ```
 */
@Composable
fun NokyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    kind: NokyButtonKind = NokyButtonKind.Primary,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    val interacao = remember { MutableInteractionSource() }
    val hover by interacao.collectIsHoveredAsState()
    val pressionado by interacao.collectIsPressedAsState()
    val cores = coresDoBotao(kind, hover && enabled, pressionado && enabled, enabled)
    val fundo by animateColorAsState(cores.fundo, tween(150))
    val texto by animateColorAsState(cores.texto, tween(150))
    val escala by animateFloatAsState(if (pressionado && enabled) 0.96f else 1f, tween(120))
    val forma = RoundedCornerShape(50)

    Row(
        modifier
            .scale(escala)
            .alpha(if (enabled) 1f else 0.5f)
            .defaultMinSize(minHeight = 38.dp)
            .clip(forma)
            .background(fundo, forma)
            .then(cores.borda?.let { Modifier.border(BorderStroke(1.dp, it), forma) } ?: Modifier)
            .hoverable(interacao, enabled)
            .clickable(interacao, indication = null, enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        if (icon != null) Icon(icon, contentDescription = null, tint = texto, modifier = Modifier.size(16.dp))
        Text(text, style = NokyTheme.type.body.copy(fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold, color = texto))
    }
}

/**
 * Quadradinho de 40 só com ícone. `contentDescription` é obrigatório (no Avalonia era o ToolTip):
 * é o que o leitor de tela fala.
 */
@Composable
fun NokyIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = NokyTheme.colors.textSecondary,
) {
    val c = NokyTheme.colors
    val interacao = remember { MutableInteractionSource() }
    val hover by interacao.collectIsHoveredAsState()
    val pressionado by interacao.collectIsPressedAsState()
    val escala by animateFloatAsState(if (pressionado) 0.92f else 1f, tween(120))
    val forma = RoundedCornerShape(12.dp)
    Box(
        modifier
            .scale(escala)
            .alpha(if (enabled) 1f else 0.5f)
            .size(40.dp)
            .clip(forma)
            .background(if (hover) c.surfaceHover else c.surface, forma)
            .border(1.dp, c.border, forma)
            .hoverable(interacao, enabled)
            .clickable(interacao, indication = null, enabled = enabled, role = Role.Button, onClick = onClick)
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
    }
}

/** Texto clicável pequeno ("Novidades", "Ver no site"). */
@Composable
fun NokyLinkButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interacao = remember { MutableInteractionSource() }
    val hover by interacao.collectIsHoveredAsState()
    val c = NokyTheme.colors
    Text(
        text,
        modifier.hoverable(interacao)
            .clickable(interacao, indication = null, role = Role.Button, onClick = onClick)
            .padding(PaddingValues(vertical = 2.dp)),
        style = NokyTheme.type.body.copy(fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, color = if (hover) c.text else c.accentText),
    )
}

/** Ícone de traço com a cor certa (padrão: texto secundário; `accent = true` usa a cor do app). */
@Composable
fun NokyIcon(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    accent: Boolean = false,
    tint: Color = if (accent) NokyTheme.colors.accentText else NokyTheme.colors.textSecondary,
) = Icon(icon, contentDescription, modifier.size(20.dp), tint = tint)
