package com.nokylabs.ui.galeria

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

/** ./gradlew :galeria:run */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NokyLabs.UI · Galeria (Compose)",
        state = rememberWindowState(width = 1080.dp, height = 900.dp),
    ) { GaleriaInterativa() }
}
