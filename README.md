# NokyLabs.UI

O **Kit NokyLabs** para apps em Avalonia: as mesmas cores, cantos, botões, cartões, animações e créditos do NokyLabs Launcher e do ValReport. **Cada app só escolhe a sua cor de destaque**; o resto (hover, fundo translúcido, cor de texto e o texto por cima do botão) é calculado sozinho e testado para continuar legível nos temas escuro e claro.

## Cor de cada app

| App | Cor | | App | Cor |
|---|---|---|---|---|
| Launcher (marca) | `#A068FF` roxo | | Avaliador Acadêmico | `#818CF8` índigo |
| YT-DLP GUI | `#6EE7B7` menta | | Blockable | `#22C55E` verde |
| CubeBin | `#C4362E` vermelho | | ValReport | `#F97316` laranja |
| HotSwap | `#3B82F6` azul | | PlaningThings | `#2DD4BF` verde-água |

Também em código: `NokyColors.YtDlpGui`, `NokyColors.CubeBin`…

Para um app novo: escolha uma cor que **não se confunda** com as de cima nem com o verde de "Instalado" (`#54D688`) e o vermelho de erro (`#F67070`), e rode os testes (`CoresTests`) com ela.

## Como usar num app

1. **Traga a biblioteca para o repositório do app** (uma vez):
   ```powershell
   git submodule add https://github.com/ThayTTG/NokyLabs.UI.git lib/NokyLabs.UI
   ```
   e no `.csproj` do app:
   ```xml
   <ProjectReference Include="..\..\lib\NokyLabs.UI\src\NokyLabs.UI\NokyLabs.UI.csproj" />
   ```
2. **Program.cs**: acrescente `.UseNokyLabs()` (registra a fonte Inter):
   ```csharp
   AppBuilder.Configure<App>().UsePlatformDetect().UseNokyLabs().LogToTrace()
   ```
3. **App.axaml**: o tema, com a cor do app (depois do FluentTheme):
   ```xml
   <Application xmlns:ui="using:NokyLabs.UI" RequestedThemeVariant="Dark">
     <Application.Styles>
       <FluentTheme />
       <ui:NokyTheme Accent="#6EE7B7" />
     </Application.Styles>
   </Application>
   ```
4. **Barra lateral**: os créditos no rodapé:
   ```xml
   <ui:CreditsCard DockPanel.Dock="Bottom" AppName="YT-DLP GUI" Version="v3.0.0" />
   ```

Para atualizar a biblioteca dentro de um app: `git submodule update --remote lib/NokyLabs.UI`.

## O que vem pronto

**Classes de estilo** (`Classes="…"`):
- Texto: `h1`, `h2`, `h3`, `secondary`, `muted`, `caps` (rótulo de seção, escreva em MAIÚSCULAS), `accent`, `num` (algarismos de largura fixa).
- Botões: `primary` (a cor do app; **um por tela**), `secondary`, `soft`, `warn`, `running`, `danger`, `ghost`, `icon` (40×40, sempre com `ToolTip.Tip`), `link`. Todos em pílula e encolhem um pouquinho ao clicar.
- Cartões: `Border.card` (+ `clickable`: sobe 2px e a borda ganha a cor do app; `soon`: apagado), `Border.stat`.
- Selos: `Border.badge` + `accent`, `success`, `warning`, `danger`, `gold`, com um `TextBlock` dentro.
- Avisos: `Border.notice` + `warning`, `danger`.
- Filtros: `RadioButton.chip`; seletor segmentado `Border.segmented` com `RadioButton.seg`.
- Navegação lateral: `RadioButton.nav`.
- Campos e barras: `TextBox.field`, `ProgressBar.slim`.
- Ícones: `<Viewbox Width="18" Height="18"><Path Classes="icon" Data="{StaticResource IconSearch}" /></Viewbox>` (lista em `Tema/Icones.axaml`).

**Animações** (as mesmas do ValReport):
- Entrada em sequência: `ui:Anim.Entrance="True" ui:Anim.Order="2"` (sobe 10px e aparece; 45 ms entre um bloco e o outro).
- Número contando: `ui:Anim.CountTo="{Binding Total}" ui:Anim.Format="{}{0:N1}%"` (formato brasileiro: 68,4%).
- Para desligar todas: `Anim.Enabled = false` (ou a variável `NOKYLABS_SEM_ANIMACAO=1`).

**Temas**: escuro (o principal) e claro, pelo `RequestedThemeVariant` do app.

## Regras do Kit
- A cor do app é para **a ação principal, o item ativo e os destaques**. Estados continuam com as cores de sempre: verde = instalado/sucesso, âmbar = atualização, vermelho = erro/apagar.
- Quando a cor do app é parecida com a de um estado (o vermelho do CubeBin e o vermelho de apagar), o estado ganha **ícone** (lixeira) e texto claro ("Apagar de vez"), nunca só a cor.
- Créditos sempre visíveis: `CreditsCard` na barra lateral (ou, num app sem janela, no "Sobre").

## Galeria
`samples/NokyLabs.UI.Galeria` mostra todas as peças, com um seletor para trocar a cor do app e o tema:
```powershell
dotnet run --project samples\NokyLabs.UI.Galeria
```

## Testes
```powershell
dotnet test
```
Conferem que, para cada cor de app, o texto em destaque fica legível (4,5:1) nos dois temas e o texto do botão tem pelo menos 3:1.

---
Idealizado e aprovado por Thayna Dias · Desenvolvido com IA (Claude, da Anthropic)
