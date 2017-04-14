package core.display.screens;

import core.display.Display;

public abstract class Screen
{
    protected Display display;
    
    public Screen(Display d) {display = d;}
    
    public Display getDisplay() {return display;}
    public void setDisplay(Display d) {display = d;}
    
    public abstract void displayOutput();    
    public abstract Screen processInput(java.awt.event.KeyEvent key);
}
