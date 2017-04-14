package core.display.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public interface Screen
{
    public void displayOutput(AsciiPanel terminal);    
    public Screen processInput(KeyEvent key);
}
