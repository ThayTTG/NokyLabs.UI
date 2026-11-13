package com.nokylabs.ui

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * Números no formato brasileiro (1.234,5), com os mesmos padrões do Avalonia:
 * `"{0:N0} apps"`, `"{0:N1}%"`, `"{0}"`.
 */
object NokyFormat {

    private val padrao = Regex("""\{0(?::N(\d))?\}""")

    fun formatValue(format: String, value: Double): String =
        padrao.replace(format) { m -> number(value, m.groupValues[1].toIntOrNull() ?: 0) }

    /** 1234567.891 com 2 casas → "1.234.567,89". */
    fun number(value: Double, decimals: Int = 0): String {
        val fator = 10.0.pow(decimals)
        val arredondado = (abs(value) * fator).roundToLong()
        val inteiro = arredondado / fator.toLong()
        val fracao = arredondado % fator.toLong()
        val inteiroTxt = inteiro.toString().reversed().chunked(3).joinToString(".").reversed()
        val sinal = if (value < 0 && arredondado != 0L) "-" else ""
        return if (decimals == 0) "$sinal$inteiroTxt"
        else "$sinal$inteiroTxt," + fracao.toString().padStart(decimals, '0')
    }
}
