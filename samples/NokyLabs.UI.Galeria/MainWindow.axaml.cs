using Avalonia;
using Avalonia.Controls;
using Avalonia.Interactivity;
using Avalonia.Markup.Xaml;
using Avalonia.Media;
using Avalonia.Styling;

namespace NokyLabs.UI.Galeria;

public partial class MainWindow : Window
{
    public MainWindow()
    {
        AvaloniaXamlLoader.Load(this);

        // Trocar a cor do app na hora
        foreach (var chip in this.FindControl<WrapPanel>("Cores")!.Children.OfType<RadioButton>())
            chip.IsCheckedChanged += (s, _) =>
            {
                if (s is RadioButton { IsChecked: true, Tag: string hex } && Kit is { } t)
                    t.Accent = Color.Parse(hex);
            };

        this.FindControl<Button>("TemaBotao")!.Click += (_, _) =>
        {
            var app = Application.Current!;
            app.RequestedThemeVariant = app.ActualThemeVariant == ThemeVariant.Dark ? ThemeVariant.Light : ThemeVariant.Dark;
        };

        // Para os testes visuais: NOKYLABS_GALERIA_COR=#6EE7B7 e NOKYLABS_GALERIA_TEMA=claro
        if (Environment.GetEnvironmentVariable("NOKYLABS_GALERIA_COR") is { } cor && Kit is { } th)
            th.Accent = Color.Parse(cor);
        if (Environment.GetEnvironmentVariable("NOKYLABS_GALERIA_TEMA") == "claro")
            Application.Current!.RequestedThemeVariant = ThemeVariant.Light;
    }

    private static NokyTheme? Kit => Application.Current?.Styles.OfType<NokyTheme>().FirstOrDefault();
}
