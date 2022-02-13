package maugrift.apwt.screens;

import maugrift.apwt.windows.Window;

/**
 * Used to indicate if a {@link Screen} uses a {@link maugrift.apwt.windows.Window} for display output.
 *
 * @param <WindowType> the type of {@link maugrift.apwt.windows.Window} used by the {@link WindowScreen}
 * @author Maugrift
 */
public interface WindowScreen<WindowType extends Window>
{
	/**
	 * Returns the {@link maugrift.apwt.windows.Window} used by this
	 * {@link Screen}.
	 *
	 * @return the {@link maugrift.apwt.windows.Window} used by this
	 *         {@link Screen}
	 */
    WindowType getWindow();
}
