using Avalonia;
using NokyLabs.UI;

namespace NokyLabs.UI.Galeria;

internal static class Program
{
    [STAThread]
    public static void Main(string[] args) =>
        AppBuilder.Configure<App>().UsePlatformDetect().UseNokyLabs().LogToTrace()
            .StartWithClassicDesktopLifetime(args);
}
