package com.nokylabs.ui.galeria

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nokylabs.ui.NokyBadge
import com.nokylabs.ui.NokyBadgeKind
import com.nokylabs.ui.NokyBrandMark
import com.nokylabs.ui.NokyButton
import com.nokylabs.ui.NokyButtonKind
import com.nokylabs.ui.NokyCard
import com.nokylabs.ui.NokyChip
import com.nokylabs.ui.NokyColors
import com.nokylabs.ui.NokyCountText
import com.nokylabs.ui.NokyCreditsCard
import com.nokylabs.ui.NokyIcon
import com.nokylabs.ui.NokyIconButton
import com.nokylabs.ui.NokyIcons
import com.nokylabs.ui.NokyLinkButton
import com.nokylabs.ui.NokyNavItem
import com.nokylabs.ui.NokyNotice
import com.nokylabs.ui.NokyNoticeKind
import com.nokylabs.ui.NokyProgressBar
import com.nokylabs.ui.NokySectionLabel
import com.nokylabs.ui.NokySegmented
import com.nokylabs.ui.NokyStat
import com.nokylabs.ui.NokyTextField
import com.nokylabs.ui.NokyTheme
import com.nokylabs.ui.nokyEntrance
import com.nokylabs.ui.toHex

/** As cores dos apps, para o seletor da Galeria. */
val CORES_DOS_APPS = listOf(
    "Just in Time" to NokyColors.JustInTime,
    "Launcher" to NokyColors.Launcher,
    "YT-DLP GUI" to NokyColors.YtDlpGui,
    "CubeBin" to NokyColors.CubeBin,
    "HotSwap" to NokyColors.HotSwap,
    "Avaliador" to NokyColors.Avaliador,
    "Blockable" to NokyColors.Blockable,
    "ValReport" to NokyColors.ValReport,
    "PlaningThings" to NokyColors.PlaningThings,
)

/** A Galeria inteira: todas as peças do Kit, na cor [accent]. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Galeria(
    accent: Color,
    dark: Boolean,
    onAccent: (Color) -> Unit = {},
    onDark: (Boolean) -> Unit = {},
    nomeDoApp: String = "Just in Time",
) {
    NokyTheme(accent = accent, dark = dark) {
        val c = NokyTheme.colors
        val t = NokyTheme.type
        Column(Modifier.background(c.bg).padding(28.dp), verticalArrangement = Arrangement.spacedBy(22.dp)) {

            // Cabeçalho
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                NokyBrandMark(44.dp)
                Column(Modifier.weight(1f)) {
                    Text("NokyLabs.UI para Compose", style = t.h1)
                    Text("Galeria · $nomeDoApp ${accent.toHex()} · tema ${if (dark) "escuro" else "claro"}", style = t.secondary)
                }
                NokySegmented(listOf("Escuro", "Claro"), if (dark) 0 else 1, { onDark(it == 0) })
            }

            // Cores dos apps
            Secao("Cor de cada app") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    CORES_DOS_APPS.forEach { (nome, cor) ->
                        val marcada = cor == accent
                        Row(
                            Modifier.clip(CircleShape).background(if (marcada) c.surfaceRaised else c.surface)
                                .border(1.dp, if (marcada) cor else c.border, CircleShape)
                                .clickable { onAccent(cor) }.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Box(Modifier.size(12.dp).clip(CircleShape).background(cor))
                            Text(nome, style = t.secondary.copy(color = if (marcada) c.text else c.textSecondary))
                        }
                    }
                }
            }

            // Prévia: um cartão no estilo do Just in Time
            Secao("Exemplo: próxima tarefa") {
                NokyCard(Modifier.fillMaxWidth().nokyEntrance(0), onClick = {}) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🎂", fontSize = 28.sp)
                        Column(Modifier.weight(1f).padding(start = 14.dp)) {
                            Text("Aniversário da Ana", style = t.h3)
                            Text("sáb., 14/11 às 20:00 · todo ano", style = t.secondary)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("02:41:07", style = t.h1.copy(color = c.accentText).merge(t.num))
                            NokyBadge("Hoje", NokyBadgeKind.Accent)
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    NokyProgressBar(0.72f, slim = true)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(22.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(22.dp)) {
                    Secao("Botões") {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NokyButton("Agendar tarefa", {}, icon = NokyIcons.Plus)
                            NokyButton("Secundário", {}, kind = NokyButtonKind.Secondary)
                            NokyButton("Suave", {}, kind = NokyButtonKind.Soft)
                            NokyButton("Atualizar", {}, kind = NokyButtonKind.Warn, icon = NokyIcons.Refresh)
                            NokyButton("Contorno", {}, kind = NokyButtonKind.Ghost)
                            NokyButton("Apagar de vez", {}, kind = NokyButtonKind.Danger, icon = NokyIcons.Trash)
                            NokyButton("Em andamento", {}, kind = NokyButtonKind.Running)
                            NokyButton("Desabilitado", {}, enabled = false)
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            NokyIconButton(NokyIcons.Search, "Buscar", {})
                            NokyIconButton(NokyIcons.Settings, "Configurações", {})
                            NokyIconButton(NokyIcons.Clock, "Prazos", {}, tint = c.accentText)
                            Spacer(Modifier.width(8.dp))
                            NokyLinkButton("Ver novidades", {})
                        }
                    }
                    Secao("Selos e avisos") {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            NokyBadge("Neutro"); NokyBadge("Destaque", NokyBadgeKind.Accent)
                            NokyBadge("Feita", NokyBadgeKind.Success); NokyBadge("Atualização", NokyBadgeKind.Warning)
                            NokyBadge("Não feita", NokyBadgeKind.Danger); NokyBadge("Ouro", NokyBadgeKind.Gold)
                        }
                        Spacer(Modifier.height(10.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NokyNotice("A cor do app é para a ação principal, o item ativo e os destaques.")
                            NokyNotice("Há uma atualização disponível.", NokyNoticeKind.Warning)
                            NokyNotice("Não foi possível agendar o alarme.", NokyNoticeKind.Danger)
                        }
                    }
                    Secao("Ícones") {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            NokyIcons.all.forEach { (nome, icone) -> NokyIcon(icone, nome, accent = nome == "Clock") }
                        }
                    }
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(22.dp)) {
                    Secao("Cartões e números") {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            NokyStat { Text("FEITAS", style = t.caps); NokyCountText(42.0, format = "{0:N0}") }
                            NokyStat { Text("NO PRAZO", style = t.caps); NokyCountText(68.4, format = "{0:N1}%") }
                        }
                        Spacer(Modifier.height(10.dp))
                        NokyCard(Modifier.fillMaxWidth(), soon = true) {
                            Text("Sincronizar com o PC", style = t.h3); Text("Em breve", style = t.muted)
                        }
                    }
                    Secao("Filtros e navegação") {
                        var filtro by remember { mutableIntStateOf(0) }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Hoje", "Semana", "Todas").forEachIndexed { i, s -> NokyChip(s, filtro == i, { filtro = i }) }
                        }
                        Spacer(Modifier.height(10.dp))
                        var aba by remember { mutableIntStateOf(0) }
                        Column(Modifier.width(230.dp).clip(RoundedCornerShape(14.dp)).background(c.sidebar).padding(8.dp)) {
                            NokyNavItem("Tarefas", aba == 0, { aba = 0 }, icon = NokyIcons.Home)
                            NokyNavItem("Histórico", aba == 1, { aba = 1 }, icon = NokyIcons.Clock)
                            NokyNavItem("Configurações", aba == 2, { aba = 2 }, icon = NokyIcons.Settings)
                        }
                    }
                    Secao("Campos e progresso") {
                        var texto by remember { mutableStateOf("") }
                        NokyTextField(texto, { texto = it }, Modifier.fillMaxWidth(), placeholder = "O que precisa ser feito?")
                        Spacer(Modifier.height(12.dp))
                        NokyProgressBar(0.4f)
                        Spacer(Modifier.height(8.dp))
                        NokyProgressBar(0.8f, slim = true)
                    }
                    Secao("Créditos") {
                        NokyCreditsCard(nomeDoApp, "v0.1.0 · prévia", Modifier.width(230.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun Secao(titulo: String, conteudo: @Composable () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        NokySectionLabel(titulo)
        conteudo()
    }
}

/** Para a Galeria interativa (janela): troca a cor do app e o tema. */
@Composable
fun GaleriaInterativa() {
    var accent by remember { mutableStateOf(NokyColors.JustInTime) }
    var dark by remember { mutableStateOf(true) }
    val nome = CORES_DOS_APPS.firstOrNull { it.second == accent }?.first ?: "App"
    Box(Modifier.verticalScroll(rememberScrollState())) {
        Galeria(accent, dark, { accent = it }, { dark = it }, nome)
    }
}
