package com.nokylabs.ui

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

/**
 * Os ícones de traço do Kit (os mesmos desenhos do `Icones.axaml`): grade 24×24, traço de 2,
 * pontas arredondadas. Use com [NokyIcon] ou com o `Icon` do Material:
 *
 * ```kotlin
 * NokyIcon(NokyIcons.Search, contentDescription = "Buscar")
 * ```
 */
object NokyIcons {
    val Logo: ImageVector by lazy { icone("Logo", "M7.5,7 V17 M7.5,11.5 A4.5,4.5 0 0 1 16.5,11.5 V17") }
    val Library: ImageVector by lazy { icone("Library", "M4,4 H10 V10 H4 Z M14,4 H20 V10 H14 Z M4,14 H10 V20 H4 Z M14,14 H20 V20 H14 Z") }
    val Updates: ImageVector by lazy { icone("Updates", "M12,4 V15 M7.5,10.5 L12,15 L16.5,10.5 M5,19.5 H19") }
    val Settings: ImageVector by lazy { icone("Settings", "M4,7 H6.5 M11.5,7 H20 M4,17 H12.5 M17.5,17 H20 M6.5,7 A2.5,2.5 0 1 0 11.5,7 A2.5,2.5 0 1 0 6.5,7 Z M12.5,17 A2.5,2.5 0 1 0 17.5,17 A2.5,2.5 0 1 0 12.5,17 Z") }
    val Search: ImageVector by lazy { icone("Search", "M4,10.5 A6.5,6.5 0 1 0 17,10.5 A6.5,6.5 0 1 0 4,10.5 Z M15.5,15.5 L20,20") }
    val Folder: ImageVector by lazy { icone("Folder", "M3,6.5 A1.5,1.5 0 0 1 4.5,5 H9 L11,7 H19.5 A1.5,1.5 0 0 1 21,8.5 V17.5 A1.5,1.5 0 0 1 19.5,19 H4.5 A1.5,1.5 0 0 1 3,17.5 Z") }
    val Trash: ImageVector by lazy { icone("Trash", "M4,7 H20 M9,7 V4.5 H15 V7 M6.5,7 L7.5,20 H16.5 L17.5,7 M10,11 V16 M14,11 V16") }
    val Sparkle: ImageVector by lazy { icone("Sparkle", "M12,3 L13.8,9.2 L20,11 L13.8,12.8 L12,19 L10.2,12.8 L4,11 L10.2,9.2 Z") }
    val Check: ImageVector by lazy { icone("Check", "M5,12.5 L10,17.5 L19,7.5") }
    val Install: ImageVector by lazy { icone("Install", "M12,3 V14 M7.5,9.5 L12,14 L16.5,9.5 M4,15 V19 A1,1 0 0 0 5,20 H19 A1,1 0 0 0 20,19 V15") }
    val Portable: ImageVector by lazy { icone("Portable", "M7,3 H17 A2,2 0 0 1 19,5 V19 A2,2 0 0 1 17,21 H7 A2,2 0 0 1 5,19 V5 A2,2 0 0 1 7,3 Z M9,7 H15 M9,17 H15") }
    val Refresh: ImageVector by lazy { icone("Refresh", "M19.5,12 A7.5,7.5 0 1 1 16.8,6.2 M17,2.8 V6.5 H13.3") }
    val Close: ImageVector by lazy { icone("Close", "M6,6 L18,18 M18,6 L6,18") }
    val Plus: ImageVector by lazy { icone("Plus", "M12,5 V19 M5,12 H19") }
    val ChevronLeft: ImageVector by lazy { icone("ChevronLeft", "M15,5 L8,12 L15,19") }
    val ChevronRight: ImageVector by lazy { icone("ChevronRight", "M9,5 L16,12 L9,19") }
    val Download: ImageVector by lazy { icone("Download", "M12,4 V15 M7.5,10.5 L12,15 L16.5,10.5 M5,19 H19") }
    val Info: ImageVector by lazy { icone("Info", "M3,12 A9,9 0 1 0 21,12 A9,9 0 1 0 3,12 Z M12,11 V17 M12,7.4 V7.6") }
    val Alert: ImageVector by lazy { icone("Alert", "M12,3.5 L21,19.5 H3 Z M12,10 V14 M12,17 V17.2") }
    val Clock: ImageVector by lazy { icone("Clock", "M3.5,12 A8.5,8.5 0 1 0 20.5,12 A8.5,8.5 0 1 0 3.5,12 Z M12,7.5 V12 L15,14") }
    val External: ImageVector by lazy { icone("External", "M14,4 H20 V10 M20,4 L11,13 M18,14 V19 A1,1 0 0 1 17,20 H5 A1,1 0 0 1 4,19 V7 A1,1 0 0 1 5,6 H10") }
    val Home: ImageVector by lazy { icone("Home", "M4,10.5 L12,4 L20,10.5 V19 A1,1 0 0 1 19,20 H14.5 V14.5 H9.5 V20 H5 A1,1 0 0 1 4,19 Z") }
    val Sun: ImageVector by lazy { icone("Sun", "M7.8,12 A4.2,4.2 0 1 0 16.2,12 A4.2,4.2 0 1 0 7.8,12 Z M12,2.5 V4.7 M12,19.3 V21.5 M4.6,4.6 L6.2,6.2 M17.8,17.8 L19.4,19.4 M2.5,12 H4.7 M19.3,12 H21.5 M4.6,19.4 L6.2,17.8 M17.8,6.2 L19.4,4.6") }
    val Moon: ImageVector by lazy { icone("Moon", "M20,14.6 A8.3,8.3 0 0 1 9.4,4 A8.4,8.4 0 1 0 20,14.6 Z") }

    /** Todos, com o nome (para a Galeria). */
    val all: List<Pair<String, ImageVector>> by lazy {
        listOf("Logo" to Logo, "Library" to Library, "Updates" to Updates, "Settings" to Settings, "Search" to Search, "Folder" to Folder, "Trash" to Trash, "Sparkle" to Sparkle, "Check" to Check, "Install" to Install, "Portable" to Portable, "Refresh" to Refresh, "Close" to Close, "Plus" to Plus, "ChevronLeft" to ChevronLeft, "ChevronRight" to ChevronRight, "Download" to Download, "Info" to Info, "Alert" to Alert, "Clock" to Clock, "External" to External, "Home" to Home, "Sun" to Sun, "Moon" to Moon)
    }

    private fun icone(nome: String, d: String): ImageVector =
        ImageVector.Builder(name = "Noky$nome", defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f)
            .addPath(
                pathData = PathParser().parsePathString(d).toNodes(),
                fill = null,
                stroke = SolidColor(Color.Black), // o "tint" do Icon troca pela cor certa
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
            .build()
}
