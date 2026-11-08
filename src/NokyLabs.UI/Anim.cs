using System.Globalization;
using Avalonia;
using Avalonia.Animation;
using Avalonia.Animation.Easings;
using Avalonia.Controls;
using Avalonia.Media;
using Avalonia.Styling;
using Avalonia.Threading;

namespace NokyLabs.UI;

/// <summary>
/// As animações do Kit NokyLabs (as mesmas do ValReport), prontas para usar no XAML:
/// <list type="bullet">
/// <item><c>ui:Anim.Entrance="True" ui:Anim.Order="2"</c>: o bloco sobe 10px e aparece, um depois do outro (45 ms de diferença).</item>
/// <item><c>ui:Anim.CountTo="{Binding Total}" ui:Anim.Format="{}{0:N0} apps"</c>: o número conta do zero até o valor.</item>
/// </list>
/// Para desligar tudo (ex.: o usuário pediu menos movimento), use <see cref="Enabled"/> = false.
/// </summary>
public static class Anim
{
    /// <summary>Liga/desliga todas as animações do Kit. Também dá para desligar com a variável NOKYLABS_SEM_ANIMACAO=1.</summary>
    public static bool Enabled { get; set; } = Environment.GetEnvironmentVariable("NOKYLABS_SEM_ANIMACAO") != "1";

    /// <summary>A curva de saída suave do Kit: cubic-bezier(.2, .7, .2, 1).</summary>
    public static readonly Easing EaseOut = new SplineEasing(0.2, 0.7, 0.2, 1);

    private static readonly CultureInfo PtBr = CultureInfo.GetCultureInfo("pt-BR");

    // ------------------------------------------------------------------ entrada em sequência

    public static readonly AttachedProperty<bool> EntranceProperty =
        AvaloniaProperty.RegisterAttached<Control, bool>("Entrance", typeof(Anim));

    public static readonly AttachedProperty<int> OrderProperty =
        AvaloniaProperty.RegisterAttached<Control, int>("Order", typeof(Anim));

    public static bool GetEntrance(Control c) => c.GetValue(EntranceProperty);
    public static void SetEntrance(Control c, bool v) => c.SetValue(EntranceProperty, v);
    public static int GetOrder(Control c) => c.GetValue(OrderProperty);
    public static void SetOrder(Control c, int v) => c.SetValue(OrderProperty, v);

    static Anim()
    {
        EntranceProperty.Changed.AddClassHandler<Control>((c, e) =>
        {
            c.AttachedToVisualTree -= OnEntranceAttached;
            if (e.NewValue is true) c.AttachedToVisualTree += OnEntranceAttached;
        });
        CountToProperty.Changed.AddClassHandler<TextBlock>((t, _) => StartCount(t));
        FormatProperty.Changed.AddClassHandler<TextBlock>((t, _) => StartCount(t));
    }

    private static void OnEntranceAttached(object? sender, VisualTreeAttachmentEventArgs e)
    {
        if (sender is Control c) PlayEntrance(c, GetOrder(c));
    }

    /// <summary>Faz o bloco entrar (dá para chamar de novo, ex.: ao trocar de aba).</summary>
    public static void PlayEntrance(Control c, int order = 0)
    {
        if (!Enabled) return;
        if (c.RenderTransform is not TranslateTransform) c.RenderTransform = new TranslateTransform();

        var animation = new Animation
        {
            Duration = TimeSpan.FromMilliseconds(450),
            Delay = TimeSpan.FromMilliseconds(Math.Min(order, 16) * 45),
            Easing = EaseOut,
            FillMode = FillMode.Backward,
            Children =
            {
                new KeyFrame { Cue = new Cue(0), Setters = { new Setter(Visual.OpacityProperty, 0d), new Setter(TranslateTransform.YProperty, 10d) } },
                new KeyFrame { Cue = new Cue(1), Setters = { new Setter(Visual.OpacityProperty, 1d), new Setter(TranslateTransform.YProperty, 0d) } },
            }
        };
        _ = animation.RunAsync(c);
    }

    // ------------------------------------------------------------------ números contando

    public static readonly AttachedProperty<double> CountToProperty =
        AvaloniaProperty.RegisterAttached<TextBlock, double>("CountTo", typeof(Anim));

    public static readonly AttachedProperty<string> FormatProperty =
        AvaloniaProperty.RegisterAttached<TextBlock, string>("Format", typeof(Anim), "{0:N0}");

    public static double GetCountTo(TextBlock t) => t.GetValue(CountToProperty);
    public static void SetCountTo(TextBlock t, double v) => t.SetValue(CountToProperty, v);
    public static string GetFormat(TextBlock t) => t.GetValue(FormatProperty);
    public static void SetFormat(TextBlock t, string v) => t.SetValue(FormatProperty, v);

    private static readonly AttachedProperty<DispatcherTimer?> TimerProperty =
        AvaloniaProperty.RegisterAttached<TextBlock, DispatcherTimer?>("CountTimer", typeof(Anim));

    /// <summary>Texto final do contador (também usado pelos testes).</summary>
    public static string FormatValue(string format, double value) => string.Format(PtBr, format, value);

    private static void StartCount(TextBlock t)
    {
        t.GetValue(TimerProperty)?.Stop();
        var target = GetCountTo(t);
        var format = GetFormat(t);

        if (!Enabled || target == 0)
        {
            t.Text = FormatValue(format, target);
            return;
        }

        var start = DateTime.UtcNow;
        var duration = TimeSpan.FromMilliseconds(750);
        var timer = new DispatcherTimer { Interval = TimeSpan.FromMilliseconds(16) };
        timer.Tick += (_, _) =>
        {
            var p = Math.Min(1, (DateTime.UtcNow - start) / duration);
            var eased = 1 - Math.Pow(1 - p, 3);
            t.Text = FormatValue(format, p >= 1 ? target : target * eased);
            if (p >= 1) timer.Stop();
        };
        t.SetValue(TimerProperty, timer);
        t.Text = FormatValue(format, 0);
        timer.Start();
    }
}
