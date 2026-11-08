using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using Avalonia.Media;
using Avalonia.Styling;

namespace NokyLabs.UI;

/// <summary>
/// O Kit NokyLabs para Avalonia. Cada app escolhe só a cor de destaque (<see cref="Accent"/>);
/// hover, pressionado, fundos translúcidos, cor de texto e a cor por cima do botão são calculados
/// por <see cref="AccentPalette"/>. Os controles padrão do Fluent (CheckBox, ToggleSwitch…)
/// também passam a usar a cor do app.
/// </summary>
public class NokyTheme : Styles
{
    public static readonly StyledProperty<Color> AccentProperty =
        AvaloniaProperty.Register<NokyTheme, Color>(nameof(Accent), NokyColors.Launcher);

    private readonly ResourceDictionary _accent = new();

    public NokyTheme()
    {
        AvaloniaXamlLoader.Load(this);
        Resources.MergedDictionaries.Add(_accent);
        ApplyAccent(Accent);
    }

    /// <summary>A cor de destaque do app (ex.: "#6EE7B7" no YT-DLP GUI).</summary>
    public Color Accent
    {
        get => GetValue(AccentProperty);
        set => SetValue(AccentProperty, value);
    }

    protected override void OnPropertyChanged(AvaloniaPropertyChangedEventArgs change)
    {
        base.OnPropertyChanged(change);
        if (change.Property == AccentProperty) ApplyAccent(Accent);
    }

    private void ApplyAccent(Color color)
    {
        var p = AccentPalette.From(color);

        _accent["AccentColor"] = p.Accent;
        _accent["AccentBrush"] = new SolidColorBrush(p.Accent);
        _accent["AccentHoverBrush"] = new SolidColorBrush(p.Hover);
        _accent["AccentPressedBrush"] = new SolidColorBrush(p.Pressed);
        _accent["AccentSoftBrush"] = new SolidColorBrush(p.Soft);
        _accent["AccentSoftHoverBrush"] = new SolidColorBrush(p.SoftHover);
        _accent["OnAccentBrush"] = new SolidColorBrush(p.OnAccent);

        // A versão para texto depende do fundo: mais clara no escuro, mais escura no claro
        _accent.ThemeDictionaries[ThemeVariant.Dark] = new ResourceDictionary
        {
            ["AccentTextColor"] = p.TextOnDark,
            ["AccentTextBrush"] = new SolidColorBrush(p.TextOnDark),
        };
        _accent.ThemeDictionaries[ThemeVariant.Light] = new ResourceDictionary
        {
            ["AccentTextColor"] = p.TextOnLight,
            ["AccentTextBrush"] = new SolidColorBrush(p.TextOnLight),
        };

        // Controles do Fluent (CheckBox, ToggleSwitch, Slider, seleção de texto…)
        _accent["SystemAccentColor"] = p.Accent;
        _accent["SystemAccentColorLight1"] = p.Hover;
        _accent["SystemAccentColorLight2"] = AccentPalette.Mix(p.Accent, Colors.White, 0.24);
        _accent["SystemAccentColorLight3"] = AccentPalette.Mix(p.Accent, Colors.White, 0.36);
        _accent["SystemAccentColorDark1"] = p.Pressed;
        _accent["SystemAccentColorDark2"] = AccentPalette.Mix(p.Accent, Colors.Black, 0.28);
        _accent["SystemAccentColorDark3"] = AccentPalette.Mix(p.Accent, Colors.Black, 0.42);
    }
}

/// <summary>Atalho para o Program.cs: registra a fonte Inter usada pelo Kit.</summary>
public static class NokyAppBuilderExtensions
{
    public static AppBuilder UseNokyLabs(this AppBuilder builder) => builder.WithInterFont();
}
