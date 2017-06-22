package boldorf.apwt.screens;

import boldorf.apwt.windows.Window;

/**
 * Used to indicate if a {@link Screen} uses a
 * {@link boldorf.apwt.windows.Window} for display output.
 * @param <WindowType> the type of {@link boldorf.apwt.windows.Window} used
 * by the {@link WindowScreen}
 */
public interface WindowScreen<WindowType extends Window>
{
    public WindowType getWindow();
}
