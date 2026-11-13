package com.nokylabs.ui

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nokylabs.ui.recursos.Res
import com.nokylabs.ui.recursos.inter_bold
import com.nokylabs.ui.recursos.inter_medium
import com.nokylabs.ui.recursos.inter_regular
import com.nokylabs.ui.recursos.inter_semibold
import org.jetbrains.compose.resources.Font

/**
 * Os textos do Kit (as classes `h1`, `h2`, `h3`, `secondary`, `muted`, `caps`, `num` do Avalonia).
 * A fonte é a Inter, que vem dentro da biblioteca.
 */
@Immutable
data class NokyTypography(
    val family: FontFamily,
    /** Título da tela. */
    val h1: TextStyle,
    /** Título de seção. */
    val h2: TextStyle,
    /** Título de cartão. */
    val h3: TextStyle,
    /** Texto normal. */
    val body: TextStyle,
    val secondary: TextStyle,
    val muted: TextStyle,
    /** Rótulo pequeno de seção. Escreva o texto em MAIÚSCULAS ("DESEMPENHO"). */
    val caps: TextStyle,
) {
    /** Números que contam ou mudam: largura fixa por dígito (use com `.merge(num)`). */
    val num = TextStyle(fontFeatureSettings = "tnum")

    fun asMaterial() = Typography(
        displayLarge = h1, headlineLarge = h1, headlineMedium = h1,
        titleLarge = h2, titleMedium = h3, titleSmall = h3,
        bodyLarge = body, bodyMedium = body, bodySmall = secondary,
        labelLarge = body.copy(fontWeight = FontWeight.SemiBold),
        labelMedium = secondary, labelSmall = muted,
    )
}

@Composable
fun interFontFamily(): FontFamily = FontFamily(
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold),
)

@Composable
internal fun nokyTypography(c: NokyColorScheme): NokyTypography {
    val f = interFontFamily()
    return NokyTypography(
        family = f,
        h1 = TextStyle(fontFamily = f, fontSize = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.4).sp, color = c.text),
        h2 = TextStyle(fontFamily = f, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = c.text),
        h3 = TextStyle(fontFamily = f, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = c.text),
        body = TextStyle(fontFamily = f, fontSize = 14.sp, color = c.text),
        secondary = TextStyle(fontFamily = f, fontSize = 13.sp, color = c.textSecondary),
        muted = TextStyle(fontFamily = f, fontSize = 12.sp, color = c.textMuted),
        caps = TextStyle(fontFamily = f, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.6.sp, color = c.textMuted),
    )
}
