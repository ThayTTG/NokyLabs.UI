package com.nokylabs.ui

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** As cores calculadas a partir da cor de cada app continuam legíveis (mesmos testes do `CoresTests.cs`). */
class CoresTests {

    private val coresDosApps = listOf(
        "#A068FF", "#6EE7B7", "#C4362E", "#3B82F6", "#818CF8", "#22C55E", "#F97316", "#2DD4BF",
        "#FFD60A", // Just in Time
    )

    @Test
    fun texto_na_cor_do_app_e_legivel_nos_dois_temas() {
        for (hex in coresDosApps) {
            val p = AccentPalette.from(corHex(hex))
            assertTrue(AccentPalette.contrast(p.textOnDark, AccentPalette.DarkSurface) >= 4.5, "escuro: $hex")
            assertTrue(AccentPalette.contrast(p.textOnLight, AccentPalette.LightSurface) >= 4.5, "claro: $hex")
        }
    }

    @Test
    fun texto_do_botao_tem_contraste_minimo() {
        for (hex in coresDosApps) {
            val p = AccentPalette.from(corHex(hex))
            assertTrue(AccentPalette.contrast(p.onAccent, p.accent) >= 3.0, hex)
        }
    }

    @Test
    fun cores_claras_ganham_texto_escuro_no_botao() {
        // menta (YT-DLP GUI), verde (Blockable), laranja (ValReport), amarelo (Just in Time)
        for (hex in listOf("#6EE7B7", "#22C55E", "#F97316", "#FFD60A"))
            assertEquals(AccentPalette.Ink, AccentPalette.from(corHex(hex)).onAccent, hex)
    }

    @Test
    fun cores_escuras_ganham_texto_branco_no_botao() {
        // roxo (Launcher), vermelho (CubeBin), azul (HotSwap)
        for (hex in listOf("#A068FF", "#C4362E", "#3B82F6"))
            assertEquals(Color.White, AccentPalette.from(corHex(hex)).onAccent, hex)
    }

    @Test
    fun fundo_suave_e_a_cor_translucida() {
        val p = AccentPalette.from(NokyColors.CubeBin)
        assertEquals(41, p.soft.a8)
        assertEquals(NokyColors.CubeBin.r8, p.soft.r8)
    }

    @Test
    fun contador_usa_o_formato_brasileiro() {
        assertEquals("68,4%", NokyFormat.formatValue("{0:N1}%", 68.4))
        assertEquals("1.234 apps", NokyFormat.formatValue("{0:N0} apps", 1234.0))
        assertEquals("1.234.567,89", NokyFormat.formatValue("{0:N2}", 1234567.891))
        assertEquals("-3,5", NokyFormat.formatValue("{0:N1}", -3.5))
    }

    /**
     * Valores conferidos com o AccentPalette.cs (mesmo algoritmo, mesmos arredondamentos):
     * se alguém mudar uma conta de um lado só, este teste avisa.
     */
    @Test
    fun mesmas_contas_do_kit_em_csharp() {
        val p = AccentPalette.from(NokyColors.Launcher)
        assertEquals("#AB7AFF", p.hover.toHex())   // 12% para o branco
        assertEquals("#8A59DB", p.pressed.toHex()) // 14% para o preto
        val amarelo = AccentPalette.from(NokyColors.JustInTime)
        assertEquals("#856F05", amarelo.textOnLight.toHex())
    }
}
