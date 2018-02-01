package maugrift.apwt.display;

import asciiPanel.AsciiPanel;
import maugrift.apwt.glyphs.ColorChar;
import maugrift.apwt.screens.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A class designed to create, contain, and operate an {@link asciiPanel.AsciiPanel}, as well as providing many utility
 * functions.
 *
 * @author Maugrift
 */
public class AsciiPanelDisplay extends JFrame implements KeyListener, Display
{
    /**
     * The {@link asciiPanel.AsciiPanel} that will be used to display all "graphical" data.
     */
    private AsciiPanel panel;

    /**
     * The current {@link maugrift.apwt.screens.Screen} being displayed by the panel and where input from the
     * KeyListener is being processed.
     */
    private Screen screen;

    /**
     * Creates a {@link AsciiPanelDisplay} to wrap a constructed {@link asciiPanel.AsciiPanel}.
     *
     * @param panel the {@link asciiPanel.AsciiPanel} to wrap with the {@link AsciiPanelDisplay}
     */
    public AsciiPanelDisplay(AsciiPanel panel)
    {
        super();
        this.panel = panel;
        add(this.panel);
        pack();
    }

    /**
     * Initializes the {@link maugrift.apwt.screens.Screen} and keylistener, as well as setting up default settings
     * outside of a constructor.
     *
     * @param startScreen the initial {@link maugrift.apwt.screens.Screen} to display
     * @return the initialized display, this
     */
    public AsciiPanelDisplay init(Screen startScreen)
    {
        screen = startScreen;
        addKeyListener(this);
        repaint();
        setResizable(false);
        setSize(getWidth() - 10, getHeight() - 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        return this;
    }

    @Override
    public void repaint()
    {
        panel.clear();
        screen.displayOutput();
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        screen = screen.processInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Returns this {@link AsciiPanelDisplay}'s {@link asciiPanel.AsciiPanel}.
     *
     * @return this {@link AsciiPanelDisplay}'s {@link asciiPanel.AsciiPanel}
     */
    public AsciiPanel getPanel()
    {
        return panel;
    }

    @Override
    public Screen getScreen()
    {
        return screen;
    }

    @Override
    public void write(int x, int y, char c, Color foreground, Color background)
    {
        panel.write(c, x, y, foreground, background);
    }

    @Override
    public int getWidthInCharacters()
    {
        return panel.getWidthInCharacters();
    }

    @Override
    public int getHeightInCharacters()
    {
        return panel.getHeightInCharacters();
    }

    @Override
    public Color getDefaultForegroundColor()
    {
        return panel.getDefaultForegroundColor();
    }

    @Override
    public Color getDefaultBackgroundColor()
    {
        return panel.getDefaultBackgroundColor();
    }
}