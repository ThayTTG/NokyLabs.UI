using Avalonia.Media;

namespace NokyLabs.UI;

/// <summary>
/// A partir da cor de destaque de um app, calcula todas as variações que o tema usa:
/// hover, pressionado, fundos translúcidos, a versão para texto e a cor do texto por cima.
/// É só conta (sem tela), então dá para testar.
/// </summary>
public sealed record AccentPalette(
    Color Accent,
    Color Hover,
    Color Pressed,
    Color Soft,
    Color SoftHover,
    Color TextOnDark,
    Color TextOnLight,
    Color OnAccent)
{
    /// <summary>Superfície dos cartões no tema escuro (o texto em destaque precisa ser lido sobre ela).</summary>
    public static readonly Color DarkSurface = Color.Parse("#1C1729");
    public static readonly Color LightSurface = Color.Parse("#FFFFFF");
    public static readonly Color Ink = Color.Parse("#110E1A");

    /// <summary>
    /// Texto branco por cima do botão só se tiver pelo menos este contraste (3:1, o mínimo para
    /// rótulos em negrito, igual ao roxo do ValReport). Abaixo disso, o texto fica escuro.
    /// </summary>
    public const double WhiteTextMinContrast = 3.0;

    public static AccentPalette From(Color accent)
    {
        accent = Color.FromRgb(accent.R, accent.G, accent.B);
        var onAccent = Contrast(Colors.White, accent) >= WhiteTextMinContrast ? Colors.White : Ink;

        return new AccentPalette(
            Accent: accent,
            Hover: Mix(accent, Colors.White, 0.12),
            Pressed: Mix(accent, Colors.Black, 0.14),
            Soft: Color.FromArgb(41, accent.R, accent.G, accent.B),      // 16%
            SoftHover: Color.FromArgb(66, accent.R, accent.G, accent.B), // 26%
            TextOnDark: Readable(accent, DarkSurface, Colors.White),
            TextOnLight: Readable(accent, LightSurface, Colors.Black),
            OnAccent: onAccent);
    }

    /// <summary>Clareia (ou escurece) a cor até ela ser lida sobre o fundo (4,5:1).</summary>
    public static Color Readable(Color color, Color background, Color towards)
    {
        for (var t = 0.0; t <= 1.0; t += 0.04)
        {
            var c = Mix(color, towards, t);
            if (Contrast(c, background) >= 4.5) return c;
        }
        return towards;
    }

    public static Color Mix(Color a, Color b, double t) => Color.FromRgb(
        (byte)Math.Round(a.R + (b.R - a.R) * t),
        (byte)Math.Round(a.G + (b.G - a.G) * t),
        (byte)Math.Round(a.B + (b.B - a.B) * t));

    /// <summary>Contraste WCAG entre duas cores (1 a 21).</summary>
    public static double Contrast(Color a, Color b)
    {
        var la = Luminance(a);
        var lb = Luminance(b);
        return (Math.Max(la, lb) + 0.05) / (Math.Min(la, lb) + 0.05);
    }

    private static double Luminance(Color c)
    {
        static double Ch(byte v)
        {
            var s = v / 255.0;
            return s <= 0.03928 ? s / 12.92 : Math.Pow((s + 0.055) / 1.055, 2.4);
        }
        return 0.2126 * Ch(c.R) + 0.7152 * Ch(c.G) + 0.0722 * Ch(c.B);
    }
}
