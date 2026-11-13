package com.nokylabs.ui

import androidx.compose.ui.graphics.Color
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * A partir da cor de destaque de um app, calcula todas as variações que o tema usa:
 * hover, pressionado, fundos translúcidos, a versão para texto e a cor do texto por cima.
 * É só conta (sem tela), então dá para testar.
 *
 * Tradução fiel do `AccentPalette.cs`: mesmas contas, mesmos arredondamentos, mesmos resultados.
 */
data class AccentPalette(
    val accent: Color,
    val hover: Color,
    val pressed: Color,
    val soft: Color,
    val softHover: Color,
    val textOnDark: Color,
    val textOnLight: Color,
    val onAccent: Color,
) {
    companion object {
        /** Superfície dos cartões no tema escuro (o texto em destaque precisa ser lido sobre ela). */
        val DarkSurface = Color(0xFF1C1729)
        val LightSurface = Color(0xFFFFFFFF)
        val Ink = Color(0xFF110E1A)

        /**
         * Texto branco por cima do botão só se tiver pelo menos este contraste (3:1, o mínimo para
         * rótulos em negrito). Abaixo disso, o texto fica escuro.
         */
        const val WHITE_TEXT_MIN_CONTRAST = 3.0

        fun from(cor: Color): AccentPalette {
            val accent = rgb(cor.r8, cor.g8, cor.b8)
            val onAccent = if (contrast(Color.White, accent) >= WHITE_TEXT_MIN_CONTRAST) Color.White else Ink
            return AccentPalette(
                accent = accent,
                hover = mix(accent, Color.White, 0.12),
                pressed = mix(accent, Color.Black, 0.14),
                soft = argb(41, accent),      // 16%
                softHover = argb(66, accent), // 26%
                textOnDark = readable(accent, DarkSurface, Color.White),
                textOnLight = readable(accent, LightSurface, Color.Black),
                onAccent = onAccent,
            )
        }

        /** Clareia (ou escurece) a cor até ela ser lida sobre o fundo (4,5:1). */
        fun readable(color: Color, background: Color, towards: Color): Color {
            var t = 0.0
            while (t <= 1.0) {
                val c = mix(color, towards, t)
                if (contrast(c, background) >= 4.5) return c
                t += 0.04
            }
            return towards
        }

        fun mix(a: Color, b: Color, t: Double): Color = rgb(
            round(a.r8 + (b.r8 - a.r8) * t).toInt(),
            round(a.g8 + (b.g8 - a.g8) * t).toInt(),
            round(a.b8 + (b.b8 - a.b8) * t).toInt(),
        )

        /** Contraste WCAG entre duas cores (1 a 21). */
        fun contrast(a: Color, b: Color): Double {
            val la = luminance(a)
            val lb = luminance(b)
            return (max(la, lb) + 0.05) / (min(la, lb) + 0.05)
        }

        private fun luminance(c: Color): Double {
            fun ch(v: Int): Double {
                val s = v / 255.0
                return if (s <= 0.03928) s / 12.92 else ((s + 0.055) / 1.055).pow(2.4)
            }
            return 0.2126 * ch(c.r8) + 0.7152 * ch(c.g8) + 0.0722 * ch(c.b8)
        }

        private fun rgb(r: Int, g: Int, b: Int) = Color(r.coerceIn(0, 255), g.coerceIn(0, 255), b.coerceIn(0, 255))
        private fun argb(a: Int, c: Color) = Color(c.r8, c.g8, c.b8, a)
    }
}

/** Os canais como bytes (0–255), igual ao Avalonia. */
val Color.r8: Int get() = (red * 255f).roundToInt()
val Color.g8: Int get() = (green * 255f).roundToInt()
val Color.b8: Int get() = (blue * 255f).roundToInt()
val Color.a8: Int get() = (alpha * 255f).roundToInt()

/** "#RRGGBB" (para mostrar e para os testes). */
fun Color.toHex(): String = "#" + listOf(r8, g8, b8).joinToString("") { it.toString(16).padStart(2, '0').uppercase() }

/** Lê "#RRGGBB" (ou "#AARRGGBB"). */
fun corHex(hex: String): Color {
    val h = hex.removePrefix("#")
    val v = h.toLong(16)
    return when (h.length) {
        6 -> Color(0xFF000000 or v)
        8 -> Color(v)
        else -> error("Cor inválida: $hex")
    }
}
