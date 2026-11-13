package com.nokylabs.ui.galeria

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.nokylabs.ui.NokyAnim
import com.nokylabs.ui.NokyColors
import org.jetbrains.skia.EncodedImageFormat
import java.io.File

/**
 * Gera imagens da Galeria sem abrir janela (./gradlew :galeria:capturar).
 * Útil para o README e para conferir o visual de longe (até pelo celular).
 */
fun main(args: Array<String>) {
    val pasta = File(args.firstOrNull() ?: "capturas").apply { mkdirs() }
    NokyAnim.enabled = false // imagem parada: sem animação de entrada
    capturar(pasta, "galeria-justintime-escuro.png", NokyColors.JustInTime, dark = true)
    capturar(pasta, "galeria-justintime-claro.png", NokyColors.JustInTime, dark = false)
    capturar(pasta, "galeria-launcher-escuro.png", NokyColors.Launcher, dark = true, nome = "Launcher")
    println("Imagens em ${pasta.absolutePath}")
}

@OptIn(androidx.compose.ui.InternalComposeUiApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
private fun capturar(pasta: File, arquivo: String, cor: Color, dark: Boolean, nome: String = "Just in Time") {
    val escala = 1.5f
    val larguraDp = 980
    val cena = ImageComposeScene(width = (larguraDp * escala).toInt(), height = (1500 * escala).toInt(), density = Density(escala)) {
        Box(Modifier.width(larguraDp.dp)) { Galeria(cor, dark, nomeDoApp = nome) }
    }
    // A fonte Inter é carregada em segundo plano: desenha algumas vezes até ela chegar
    var tempo = 0L
    repeat(12) { cena.render(tempo); tempo += 100_000_000L; Thread.sleep(120) }
    val imagem = cena.render(tempo)
    // Corta o espaço vazio de baixo: mede a altura real do conteúdo
    val altura = cena.calculateContentSize().height.coerceAtMost(imagem.height)
    val recorte = org.jetbrains.skia.Image.makeFromBitmap(
        org.jetbrains.skia.Bitmap().also { bmp ->
            bmp.allocN32Pixels(imagem.width, altura)
            imagem.readPixels(bmp, 0, 0)
        }
    )
    File(pasta, arquivo).writeBytes(recorte.encodeToData(EncodedImageFormat.PNG)!!.bytes)
    cena.close()
}
