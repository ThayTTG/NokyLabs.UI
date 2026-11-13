using Avalonia.Media;
using NokyLabs.UI;
using Xunit;

namespace NokyLabs.UI.Tests;

/// <summary>As cores calculadas a partir da cor de cada app continuam legíveis.</summary>
public sealed class CoresTests
{
    public static TheoryData<string> CoresDosApps => new()
    {
        "#A068FF", "#6EE7B7", "#C4362E", "#3B82F6", "#818CF8", "#22C55E", "#F97316", "#2DD4BF",
        "#FFD60A", // Just in Time
    };

    [Theory, MemberData(nameof(CoresDosApps))]
    public void Texto_na_cor_do_app_e_legivel_nos_dois_temas(string hex)
    {
        var p = AccentPalette.From(Color.Parse(hex));

        Assert.True(AccentPalette.Contrast(p.TextOnDark, AccentPalette.DarkSurface) >= 4.5);
        Assert.True(AccentPalette.Contrast(p.TextOnLight, AccentPalette.LightSurface) >= 4.5);
    }

    [Theory, MemberData(nameof(CoresDosApps))]
    public void Texto_do_botao_tem_contraste_minimo(string hex)
    {
        var p = AccentPalette.From(Color.Parse(hex));

        Assert.True(AccentPalette.Contrast(p.OnAccent, p.Accent) >= 3.0);
    }

    [Theory]
    [InlineData("#6EE7B7")] // menta (YT-DLP GUI)
    [InlineData("#22C55E")] // verde (Blockable)
    [InlineData("#F97316")] // laranja (ValReport)
    [InlineData("#FFD60A")] // amarelo (Just in Time)
    public void Cores_claras_ganham_texto_escuro_no_botao(string hex) =>
        Assert.Equal(AccentPalette.Ink, AccentPalette.From(Color.Parse(hex)).OnAccent);

    [Theory]
    [InlineData("#A068FF")] // roxo (Launcher)
    [InlineData("#C4362E")] // vermelho (CubeBin)
    [InlineData("#3B82F6")] // azul (HotSwap)
    public void Cores_escuras_ganham_texto_branco_no_botao(string hex) =>
        Assert.Equal(Colors.White, AccentPalette.From(Color.Parse(hex)).OnAccent);

    [Fact]
    public void Fundo_suave_e_a_cor_translucida()
    {
        var p = AccentPalette.From(NokyColors.CubeBin);
        Assert.Equal(41, p.Soft.A);
        Assert.Equal(NokyColors.CubeBin.R, p.Soft.R);
    }

    [Fact]
    public void Contador_usa_o_formato_brasileiro() =>
        Assert.Equal("68,4%", Anim.FormatValue("{0:N1}%", 68.4));
}
