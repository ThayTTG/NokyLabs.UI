package com.nokylabs.ui

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/** A cor de destaque de cada app da NokyLabs (decidida em 08/11/2026; Just in Time em 13/11/2026). */
object NokyColors {
    val Launcher = Color(0xFFA068FF)      // roxo, a cor da marca
    val YtDlpGui = Color(0xFF6EE7B7)      // menta
    val CubeBin = Color(0xFFC4362E)       // vermelho
    val HotSwap = Color(0xFF3B82F6)       // azul
    val Avaliador = Color(0xFF818CF8)     // índigo
    val Blockable = Color(0xFF22C55E)     // verde
    val ValReport = Color(0xFFF97316)     // laranja
    val PlaningThings = Color(0xFF2DD4BF) // verde-água
    val JustInTime = Color(0xFFFFD60A)    // amarelo-limão (tempo)

    /** Iguais nos dois temas. */
    val Gold = Color(0xFFF2B33D)
    val BrandPurple = Color(0xFFA068FF)
    val BrandOrange = Color(0xFFEC7518)

    /** Gradiente da marca (o quadradinho do "n"). */
    val BrandGradient = Brush.linearGradient(listOf(Color(0xFF6D28D9), Color(0xFFC084FC)))
}

/** As cores neutras de um tema (iguais às do `Cores.axaml`). */
data class NokyNeutrals(
    val bg: Color,
    val sidebar: Color,
    val surface: Color,
    val surfaceHover: Color,
    val surfaceRaised: Color,
    val border: Color,
    val text: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val success: Color,
    val warning: Color,
    val danger: Color,
    val shadow: Color,
    val stat: Color,
) {
    val successSoft get() = success.copy(alpha = 0.16f)
    val warningSoft get() = warning.copy(alpha = 0.16f)
    val warningSoftHover get() = warning.copy(alpha = 0.26f)
    val dangerSoft get() = danger.copy(alpha = 0.16f)

    companion object {
        /** Escuro (o principal). */
        val Dark = NokyNeutrals(
            bg = Color(0xFF110E1A),
            sidebar = Color(0xFF151120),
            surface = Color(0xFF1C1729),
            surfaceHover = Color(0xFF251F36),
            surfaceRaised = Color(0xFF2F2843),
            border = Color(0xFF342B4B),
            text = Color(0xFFF0ECF8),
            textSecondary = Color(0xFFC9C1DB),
            textMuted = Color(0xFF968DAC),
            success = Color(0xFF54D688),
            warning = Color(0xFFF6B84C),
            danger = Color(0xFFF67070),
            shadow = Color(0x59000000),
            stat = Color(0x1AFFFFFF),
        )

        /** Claro. */
        val Light = NokyNeutrals(
            bg = Color(0xFFFAF6F1),
            sidebar = Color(0xFFF5EEE6),
            surface = Color(0xFFFFFFFF),
            surfaceHover = Color(0xFFF5EEE6),
            surfaceRaised = Color(0xFFECE2D6),
            border = Color(0xFFE6DACD),
            text = Color(0xFF241B14),
            textSecondary = Color(0xFF4A3D33),
            textMuted = Color(0xFF7D6E62),
            success = Color(0xFF1B9A57),
            warning = Color(0xFFB77B09),
            danger = Color(0xFFD64545),
            shadow = Color(0x1A46280A),
            stat = Color(0x0F46280A),
        )
    }
}
