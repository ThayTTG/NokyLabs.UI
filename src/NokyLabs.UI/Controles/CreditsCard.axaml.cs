using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;

namespace NokyLabs.UI;

/// <summary>
/// Créditos que todo app da NokyLabs mostra: "Idealizado e aprovado por Thayna Dias" e
/// "Desenvolvido com IA (Claude, da Anthropic)". Vai no rodapé da barra lateral.
/// </summary>
public partial class CreditsCard : UserControl
{
    public static readonly StyledProperty<string> AppNameProperty =
        AvaloniaProperty.Register<CreditsCard, string>(nameof(AppName), "NokyLabs");

    public static readonly StyledProperty<string?> VersionProperty =
        AvaloniaProperty.Register<CreditsCard, string?>(nameof(Version));

    public CreditsCard() => AvaloniaXamlLoader.Load(this);

    public string AppName
    {
        get => GetValue(AppNameProperty);
        set => SetValue(AppNameProperty, value);
    }

    /// <summary>Ex.: "v3.0.0 · prévia". Vazio esconde a linha.</summary>
    public string? Version
    {
        get => GetValue(VersionProperty);
        set => SetValue(VersionProperty, value);
    }
}
