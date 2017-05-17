package corelib.display.screens;

import corelib.display.windows.Window;

/**
 * Used to indicate if a {@link Screen} uses a
 * {@link corelib.display.windows.Window} for display output.
 * @param <WindowType> the type of {@link corelib.display.windows.Window} used
 * by the {@link WindowScreen}
 */
public interface WindowScreen<WindowType extends Window>
{
    public WindowType getWindow();
}
