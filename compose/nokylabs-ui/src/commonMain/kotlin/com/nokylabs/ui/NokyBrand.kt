package com.nokylabs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Créditos que todo app da NokyLabs mostra: "Idealizado e aprovado por Thayna Dias" e
 * "Desenvolvido com IA (Claude, da Anthropic)". No PC vai no rodapé da barra lateral;
 * no celular, na tela "Sobre".
 */
@Composable
fun NokyCreditsCard(appName: String = "NokyLabs", version: String? = null, modifier: Modifier = Modifier) {
    val c = NokyTheme.colors
    val t = NokyTheme.type
    val forma = RoundedCornerShape(12.dp)
    Column(
        modifier.clip(forma).background(c.surface, forma).border(1.dp, c.border, forma)
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Text(appName, style = t.body.copy(fontSize = 12.sp, fontWeight = FontWeight.SemiBold))
        if (!version.isNullOrEmpty()) Text(version, style = t.muted)
        NokyDivider(Modifier.padding(top = 8.dp, bottom = 6.dp))
        Text("Idealizado e aprovado por\nThayna Dias", style = t.body.copy(fontSize = 11.sp, fontWeight = FontWeight.SemiBold))
        Text("Desenvolvido com IA\n(Claude, da Anthropic)", style = t.muted.copy(fontSize = 11.sp), modifier = Modifier.padding(top = 2.dp))
    }
}

/** O quadradinho da marca: gradiente roxo com o "n" do NokyLabs. */
@Composable
fun NokyBrandMark(size: Dp = 36.dp, modifier: Modifier = Modifier) {
    val forma = RoundedCornerShape(size * 0.28f)
    Box(modifier.size(size).clip(forma).background(NokyColors.BrandGradient, forma), contentAlignment = Alignment.Center) {
        Icon(NokyIcons.Logo, contentDescription = "NokyLabs", tint = Color.White, modifier = Modifier.size(size * 0.66f))
    }
}

/** Cabeçalho de seção: rótulo em maiúsculas (estilo `caps`) com espaço embaixo. */
@Composable
fun NokySectionLabel(text: String, modifier: Modifier = Modifier) =
    Text(text.uppercase(), modifier.fillMaxWidth().padding(bottom = 8.dp), style = NokyTheme.type.caps)
