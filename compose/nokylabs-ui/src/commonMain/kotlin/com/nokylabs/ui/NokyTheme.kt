package com.nokylabs.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * As cores que os componentes usam: as neutras do tema + as variações da cor do app.
 * `accentText` já é a versão certa para o tema atual (mais clara no escuro, mais escura no claro).
 */
@Immutable
data class NokyColorScheme(
    val neutrals: NokyNeutrals,
    val palette: AccentPalette,
    val dark: Boolean,
) {
    val accent get() = palette.accent
    val accentHover get() = palette.hover
    val accentPressed get() = palette.pressed
    val accentSoft get() = palette.soft
    val accentSoftHover get() = palette.softHover
    val onAccent get() = palette.onAccent
    val accentText get() = if (dark) palette.textOnDark else palette.textOnLight

    // Atalhos para as neutras (NokyTheme.colors.surface em vez de .neutrals.surface)
    val bg get() = neutrals.bg
    val sidebar get() = neutrals.sidebar
    val surface get() = neutrals.surface
    val surfaceHover get() = neutrals.surfaceHover
    val surfaceRaised get() = neutrals.surfaceRaised
    val border get() = neutrals.border
    val text get() = neutrals.text
    val textSecondary get() = neutrals.textSecondary
    val textMuted get() = neutrals.textMuted
    val success get() = neutrals.success
    val warning get() = neutrals.warning
    val danger get() = neutrals.danger
}

private val LocalNokyColors = staticCompositionLocalOf<NokyColorScheme> {
    error("Use os componentes do Kit dentro de NokyTheme { … }")
}
private val LocalNokyTypography = staticCompositionLocalOf<NokyTypography> {
    error("Use os componentes do Kit dentro de NokyTheme { … }")
}

/**
 * O Kit NokyLabs para Compose. Cada app escolhe só a cor de destaque; o resto é calculado.
 *
 * ```kotlin
 * NokyTheme(accent = NokyColors.JustInTime) {
 *     // telas do app
 * }
 * ```
 *
 * Os componentes do Material 3 (Checkbox, Switch, Slider, seleção de texto…) também passam a
 * usar a cor do app, como os controles do Fluent no Kit em Avalonia.
 */
@Composable
fun NokyTheme(
    accent: Color = NokyColors.Launcher,
    dark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = remember(accent, dark) {
        NokyColorScheme(if (dark) NokyNeutrals.Dark else NokyNeutrals.Light, AccentPalette.from(accent), dark)
    }
    val typography = nokyTypography(colors)

    val n = colors.neutrals
    val material = if (dark) darkColorScheme(
        primary = colors.accent, onPrimary = colors.onAccent,
        primaryContainer = colors.accentSoft, onPrimaryContainer = colors.text,
        secondary = colors.accentText, onSecondary = colors.onAccent,
        background = n.bg, onBackground = n.text,
        surface = n.surface, onSurface = n.text,
        surfaceVariant = n.surfaceRaised, onSurfaceVariant = n.textSecondary,
        surfaceContainer = n.surface, surfaceContainerHigh = n.surfaceRaised,
        outline = n.border, outlineVariant = n.border,
        error = n.danger, onError = Color.White,
    ) else lightColorScheme(
        primary = colors.accent, onPrimary = colors.onAccent,
        primaryContainer = colors.accentSoft, onPrimaryContainer = colors.text,
        secondary = colors.accentText, onSecondary = colors.onAccent,
        background = n.bg, onBackground = n.text,
        surface = n.surface, onSurface = n.text,
        surfaceVariant = n.surfaceRaised, onSurfaceVariant = n.textSecondary,
        surfaceContainer = n.surface, surfaceContainerHigh = n.surfaceRaised,
        outline = n.border, outlineVariant = n.border,
        error = n.danger, onError = Color.White,
    )

    MaterialTheme(colorScheme = material, typography = typography.asMaterial()) {
        CompositionLocalProvider(
            LocalNokyColors provides colors,
            LocalNokyTypography provides typography,
            LocalContentColor provides n.text,
            LocalTextSelectionColors provides TextSelectionColors(colors.accent, colors.accentSoftHover),
            content = content,
        )
    }
}

/** Acesso às cores e textos do Kit: `NokyTheme.colors.surface`, `NokyTheme.type.h2`. */
object NokyTheme {
    val colors: NokyColorScheme @Composable get() = LocalNokyColors.current
    val type: NokyTypography @Composable get() = LocalNokyTypography.current
}

/** Estilo de texto com a cor trocada (atalho). */
fun TextStyle.cor(c: Color) = copy(color = c)
