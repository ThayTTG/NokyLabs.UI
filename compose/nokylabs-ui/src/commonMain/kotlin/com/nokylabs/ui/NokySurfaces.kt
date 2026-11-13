package com.nokylabs.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Cartão do Kit. Com `onClick`, vira clicável: sobe 2 e a borda ganha a cor do app (no hover
 * do PC ou enquanto o dedo está em cima). `soon = true` deixa apagado ("Em breve").
 */
@Composable
fun NokyCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    soon: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    val c = NokyTheme.colors
    val interacao = remember { MutableInteractionSource() }
    val hover by interacao.collectIsHoveredAsState()
    val pressionado by interacao.collectIsPressedAsState()
    val ativo = onClick != null && (hover || pressionado)
    val fundo by animateColorAsState(if (ativo) c.surfaceHover else c.surface, tween(150))
    val borda by animateColorAsState(if (ativo) c.accentSoftHover else c.border, tween(150))
    val subida by animateDpAsState(if (ativo) (-2).dp else 0.dp, tween(200))
    val forma = RoundedCornerShape(16.dp)
    Column(
        modifier
            .offset(y = subida)
            .alpha(if (soon) 0.65f else 1f)
            .clip(forma)
            .background(fundo, forma)
            .border(1.dp, borda, forma)
            .then(
                if (onClick != null) Modifier.hoverable(interacao).clickable(interacao, indication = null, onClick = onClick)
                else Modifier
            )
            .padding(contentPadding),
        content = content,
    )
}

/** Número em destaque dentro de um cartão (banner, painel). */
@Composable
fun NokyStat(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val forma = RoundedCornerShape(12.dp)
    Column(
        modifier.defaultMinSize(minWidth = 104.dp)
            .clip(forma).background(NokyTheme.colors.neutrals.stat, forma)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        content = content,
    )
}

/** Tipos de selo. */
enum class NokyBadgeKind { Neutral, Accent, Success, Warning, Danger, Gold }

/** Selo em pílula: `NokyBadge("Instalado", NokyBadgeKind.Success)`. */
@Composable
fun NokyBadge(text: String, kind: NokyBadgeKind = NokyBadgeKind.Neutral, modifier: Modifier = Modifier) {
    val c = NokyTheme.colors
    val (fundo, texto) = when (kind) {
        NokyBadgeKind.Neutral -> c.surfaceRaised to c.textSecondary
        NokyBadgeKind.Accent -> c.accentSoft to c.accentText
        NokyBadgeKind.Success -> c.neutrals.successSoft to c.success
        NokyBadgeKind.Warning -> c.neutrals.warningSoft to c.warning
        NokyBadgeKind.Danger -> c.neutrals.dangerSoft to c.danger
        NokyBadgeKind.Gold -> NokyColors.Gold.copy(alpha = 0.22f) to c.text
    }
    val forma = RoundedCornerShape(50)
    Box(modifier.clip(forma).background(fundo, forma).padding(horizontal = 10.dp, vertical = 3.dp)) {
        Text(text, style = NokyTheme.type.body.copy(fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold, color = texto))
    }
}

/** Tipos de aviso dentro da tela. */
enum class NokyNoticeKind { Accent, Warning, Danger }

/** Aviso dentro da tela, com ícone opcional (o ícone ganha a cor do tipo). */
@Composable
fun NokyNotice(
    text: String,
    kind: NokyNoticeKind = NokyNoticeKind.Accent,
    modifier: Modifier = Modifier,
    icon: ImageVector? = when (kind) {
        NokyNoticeKind.Accent -> NokyIcons.Info
        NokyNoticeKind.Warning, NokyNoticeKind.Danger -> NokyIcons.Alert
    },
) {
    val c = NokyTheme.colors
    val (fundo, corIcone) = when (kind) {
        NokyNoticeKind.Accent -> c.accentSoft to c.accentText
        NokyNoticeKind.Warning -> c.neutrals.warningSoft to c.warning
        NokyNoticeKind.Danger -> c.neutrals.dangerSoft to c.danger
    }
    val forma = RoundedCornerShape(10.dp)
    Row(
        modifier.clip(forma).background(fundo, forma).padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top,
    ) {
        if (icon != null) Icon(icon, null, Modifier.size(18.dp), tint = corIcone)
        Text(text, style = NokyTheme.type.body.copy(fontSize = 13.5.sp, color = c.textSecondary))
    }
}

/** Linha fina de separação. */
@Composable
fun NokyDivider(modifier: Modifier = Modifier, thickness: Dp = 1.dp, color: Color = NokyTheme.colors.border) =
    Box(modifier.fillMaxWidth().height(thickness).background(color))
