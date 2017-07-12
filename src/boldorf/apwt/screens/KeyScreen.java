package boldorf.apwt.screens;

import boldorf.apwt.screens.Keybinding;
import java.util.List;

/** A {@link Screen} with {@link Keybinding Keybindings}. */
public interface KeyScreen
{
    /**
     * Returns a List of {@link Keybinding Keybindings} active on this
     * {@link KeyScreen}.
     * @return a List of {@link Keybinding Keybindings} active on this
     * {@link KeyScreen}
     */
    public List<Keybinding> getKeybindings();
}
