package maugrift.apwt.screens;

import java.util.List;

/**
 * A {@link Screen} with {@link Keybinding Keybindings}.
 *
 * @author Maugrift
 */
public interface KeyScreen
{
    /**
     * Returns a List of {@link Keybinding Keybindings} active on this {@link KeyScreen}.
     *
     * @return a List of {@link Keybinding Keybindings} active on this {@link KeyScreen}
     */
    public List<Keybinding> getKeybindings();
}
